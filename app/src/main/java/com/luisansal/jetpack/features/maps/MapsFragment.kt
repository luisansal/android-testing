package com.luisansal.jetpack.features.maps

import android.Manifest
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.luisansal.jetpack.R
import com.luisansal.jetpack.base.BaseFragment
import com.luisansal.jetpack.common.interfaces.TitleListener
import com.luisansal.jetpack.data.preferences.UserSharedPreferences
import com.luisansal.jetpack.domain.entity.Visit
import com.luisansal.jetpack.features.manageusers.viewmodel.UserViewModel
import com.luisansal.jetpack.utils.hideKeyboardFrom
import com.luisansal.jetpack.utils.injectFragment
import kotlinx.android.synthetic.main.maps_fragment.*
import okhttp3.*
import org.json.JSONObject
import org.koin.android.ext.android.inject

class MapsFragment : BaseFragment(), OnMapReadyCallback, TitleListener {

    private val mViewModel: MapsViewModel by injectFragment()

    override val title = "Maps Manager"

    private var mGoogleMap: GoogleMap? = null

    private var mLocationPermissionGranted: Boolean = false
    private val mDefaultLocation = LatLng(0.0, 0.0)
    private var mLastKnownLocation: Location? = null

    // Construct a FusedLocationProviderClient.
    private val mFusedLocationProviderClient by lazy { LocationServices.getFusedLocationProviderClient(requireContext()) }

    // Construct a PlaceDetectionClient.
    private val mPlaceDetectionClient by lazy { Places.getPlaceDetectionClient(requireActivity(), null) }

    // Construct a GeoDataClient.
    private val locationManager by lazy { requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager }
    private val userSharedPreferences by inject<UserSharedPreferences>()
    private var lastUserMarker: Marker? = null
    private var lastOtherMarker: Marker? = null

    private val websocket by lazy {
        val client = OkHttpClient()
        val request = Request.Builder().url("ws://192.168.8.131:8092").build()
        client.newWebSocket(request, webSocketListener)
    }

    fun setupWebSocket(){
        val jsonObject = JSONObject()
        jsonObject.put("command", "subscribe")
        jsonObject.put("channel", CHANNEL_ID)
        websocket.send(jsonObject.toString())
    }

    private val webSocketListener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)

            requireActivity().runOnUiThread {
                Toast.makeText(activity, "Maps Conexión establecida", Toast.LENGTH_LONG).show()
            }
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            Log.d("socket failure", t.message)
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)

            requireActivity().runOnUiThread {
                val model = WebSocketModel(text)
                val user = model.message
                val descriptionSplited = model.description.split(";")
                val latitude = descriptionSplited[0].toDouble()
                val longitude = descriptionSplited[1].toDouble()

                lastOtherMarker?.remove()
                lastOtherMarker = mGoogleMap?.addMarker(
                        MarkerOptions().position(LatLng(latitude, longitude))
                                .title("Marker user: " + user)
                                .icon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_coche_ecologico))
                )
                Toast.makeText(activity, "onMessage: ${model.message} ${latitude}-${longitude} ", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun bitmapDescriptorFromVector(context: Context, @DrawableRes vectorResId: Int): BitmapDescriptor? {
        val iconSize = 150
        val vectorDrawable: Drawable? = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable?.setBounds(0, 0, iconSize, iconSize)
        val bitmap = Bitmap.createBitmap(iconSize, iconSize, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable?.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    fun sendRealTracking(name: String?, location: Location?) {
        val jsonObjectMessage = JSONObject()
        jsonObjectMessage.put("command", "groupchat")
        jsonObjectMessage.put("channel", CHANNEL_ID)
        jsonObjectMessage.put("message", name)
        jsonObjectMessage.put("description", "${location!!.latitude};${location.longitude}")

        websocket.send(jsonObjectMessage.toString())
    }

    internal class WebSocketModel(json: String) : JSONObject(json) {
        val message: String = this.optString("message")
        val description: String = this.optString("description")
    }

    override fun getViewIdResource() = R.layout.maps_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapView = wrapMap as MapView?
        mapView?.onCreate(savedInstanceState)
        mapView?.onResume()

        mapView?.getMapAsync(this)

        mViewModel.mapViewState.observe(::getLifecycle, ::mapsObserve)
        setupWebSocket()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // Add a marker in Sydney, Australia, and move the camera.
        mGoogleMap = googleMap

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI()

        // Get the current location of the device and set the position of the map.
        getDeviceLocation()

        onClickBtnMostrarVisitas()
        onACBuscarVisitasTextChanged()
        onACBuscarLugaresTextChanged()
        onClickBtnCurrentLocation()
        onClickMap()

        initTracking()
    }

    fun initTracking() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0F, object : LocationListener {
                override fun onLocationChanged(location: Location?) {
                    this@MapsFragment.onLocationChanged(location)
                    sendRealTracking(userSharedPreferences.name, location)
                }

                override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) = Unit
                override fun onProviderEnabled(p0: String?) = Unit
                override fun onProviderDisabled(p0: String?) = Unit
            })
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0F, object : LocationListener {
                override fun onLocationChanged(location: Location?) {
                    this@MapsFragment.onLocationChanged(location)
                    sendRealTracking(userSharedPreferences.name, location)
                }

                override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) = Unit
                override fun onProviderEnabled(p0: String?) = Unit
                override fun onProviderDisabled(p0: String?) = Unit
            })
        }
    }

    fun onLocationChanged(location: Location?) {
        val user = UserViewModel.user
        location?.let { location ->
            lastUserMarker?.remove()
            lastUserMarker = mGoogleMap?.addMarker(MarkerOptions().position(LatLng(location.latitude, location.longitude)).title("Marker user: " + user?.name))
        }
    }

    fun onClickBtnCurrentLocation() {
        imvCurrentLocation.setOnClickListener {
            getDeviceLocation()
        }
    }

    private fun mostrarvisitas(dni: String) {
        mGoogleMap?.clear()
        mViewModel.getVisits(dni)
        afterClickShowVisits()
    }

    fun onClickBtnMostrarVisitas() {
        btnMostrarVisitas.setOnClickListener { mostrarvisitas(acBuscarVisitas.text.toString()) }
    }

    fun onClickMap() {
        mGoogleMap?.setOnMapClickListener { location ->
            mGoogleMap?.clear()
            lastUserMarker = mGoogleMap?.addMarker(MarkerOptions().position(location).title("Marker user: " + UserViewModel.user?.name))

            val visit = Visit(location = location)
            UserViewModel.user?.id?.let { mViewModel.saveOneVisitForUser(visit, it) }
        }
    }

    fun mapsObserve(mapsViewState: MapsViewState) {
        when (mapsViewState) {
            is MapsViewState.ErrorState -> {
                Toast.makeText(context, mapsViewState.error.toString(), Toast.LENGTH_LONG).show()
            }
            is MapsViewState.LoadingState -> {
                Log.d("Loading", "Loading")
            }
            is MapsViewState.SuccessUserSavedState -> {
                val response = mapsViewState.data
                if (response)
                    Toast.makeText(context, "Posición guardada", Toast.LENGTH_LONG).show()
            }
            is MapsViewState.SuccessVisistsState -> {
                val response = mapsViewState.data
                if (response != null)
                    for (visit in response.visits) {
                        mGoogleMap?.addMarker(MarkerOptions().position(visit.location).title("Marker user: " + response.user.name))
                        mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLng(visit.location))
                    }
            }
        }
    }

    fun afterClickShowVisits() {
        acBuscarVisitas.hideKeyboardFrom(requireContext())
        acBuscarVisitas.clearFocus()
    }

    fun onACBuscarLugaresTextChanged() {
        acBuscarLugares.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) = Unit
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                //acBuscarLugares.setAdapter()
            }

            override fun afterTextChanged(editable: Editable) = Unit
        })
    }

    fun onACBuscarVisitasTextChanged() {
        acBuscarVisitas.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) = Unit
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                //                acBuscarLugares.setAdapter()
            }

            override fun afterTextChanged(editable: Editable) = Unit
        })
    }

    private fun getLocationPermission() {
        /*
      * Request location permission, so that we can get the location of the
      * device. The result of the permission request is handled by a callback,
      * onRequestPermissionsResult.
      */
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        mLocationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true
                }
            }
        }
        updateLocationUI()
    }

    private fun updateLocationUI() {
        if (mGoogleMap == null) {
            return
        }
        try {
            if (mLocationPermissionGranted) {
                mGoogleMap?.isMyLocationEnabled = true
                mGoogleMap?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                mGoogleMap?.isMyLocationEnabled = false
                mGoogleMap?.uiSettings?.isMyLocationButtonEnabled = false
                mLastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message)
        }
    }

    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                val locationResult = mFusedLocationProviderClient?.lastLocation
                locationResult?.addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        mLastKnownLocation = task.result as Location?
                        mLastKnownLocation?.latitude?.let {
                            mLastKnownLocation?.longitude?.let { it1 ->
                                mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it, it1), DEFAULT_ZOOM.toFloat()))
                                lastUserMarker?.remove()
                                lastUserMarker = mGoogleMap?.addMarker(MarkerOptions().position(LatLng(it, it1)).title("Marker user: " + UserViewModel.user?.name))
                            }
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")
                        Log.e(TAG, "Exception: %s", task.exception)
                        mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM.toFloat()))
                        mGoogleMap?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message)
        }

    }

    companion object {
        private val TAG = MapsFragment::class.java.getName()
        private val DEFAULT_ZOOM = 18
        val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        const val CHANNEL_ID = "MAPS_GLOBAL_ANDROID"
        fun newInstance(): MapsFragment {
            return MapsFragment()
        }
    }
}
