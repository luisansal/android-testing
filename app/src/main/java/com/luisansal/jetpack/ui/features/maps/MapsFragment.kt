package com.luisansal.jetpack.ui.features.maps

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.luisansal.jetpack.R
import com.luisansal.jetpack.common.interfaces.TitleListener
import com.luisansal.jetpack.domain.entity.Visit
import com.luisansal.jetpack.ui.features.maps.model.MarkerUserVisitMapModel
import com.luisansal.jetpack.ui.utils.injectFragment
import com.luisansal.jetpack.ui.viewstate.BaseViewState
import kotlinx.android.synthetic.main.maps_fragment.*

class MapsFragment : Fragment(), OnMapReadyCallback, TitleListener, GoogleMap.OnMapClickListener {

    private val mViewModel: MapsViewModel by injectFragment()

    override val title = "Maps Manager"

    private var mGoogleMap: GoogleMap? = null

    private var mLocationPermissionGranted: Boolean = false
    private val mDefaultLocation = LatLng(-33.8523341, 151.2106085)
    private var mLastKnownLocation: Location? = null
    // Construct a FusedLocationProviderClient.
    private val mFusedLocationProviderClient by lazy {
        context?.let { LocationServices.getFusedLocationProviderClient(it) }
    }
    // Construct a PlaceDetectionClient.
    private val mPlaceDetectionClient by lazy {
        Places.getPlaceDetectionClient(requireActivity(), null)
    }
    // Construct a GeoDataClient.
    private val mGeoDataClient by lazy {
        Places.getGeoDataClient(requireActivity(), null)
    }

    private var dni: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.maps_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        onClickBtnMostrarVisitas()
        onACBuscarLugaresTextChanged()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //mViewModel.
        // TODO: Use the ViewModel
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // Add a marker in Sydney, Australia, and move the camera.
        mGoogleMap = googleMap

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI()

        // Get the current location of the device and set the position of the map.
        getDeviceLocation()

        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-33.852, 151.211);
//        mGoogleMap?.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    private fun mostrarvisitas(dni: String) {
        mGoogleMap?.clear()
        mViewModel.getVisits(dni)
    }

    fun onClickBtnMostrarVisitas() {
        mViewModel.mapViewState.observe(::getLifecycle, ::observerVisitas)
        btnMostrarVisitas.setOnClickListener { mostrarvisitas(acBuscarLugares.text.toString()) }
    }

    fun observerVisitas(baseViewState: BaseViewState) {
        when (baseViewState) {
            is BaseViewState.LoadingState -> {

            }
            is BaseViewState.SuccessState<*> -> {
                val response = baseViewState.data?.let { it as MarkerUserVisitMapModel }
                if (response != null)
                    for (visit in response.visits) {
                        mGoogleMap?.addMarker(MarkerOptions().position(visit.location).title("Marker user: " + response.user.name))
                        mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLng(visit.location))
                    }
            }
        }
    }

    fun onACBuscarLugaresTextChanged() {
        acBuscarLugares.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                //                acBuscarLugares.setAdapter();
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
    }

    fun situarUbicacionActual() {

    }

    private fun getLocationPermission() {
        /*
      * Request location permission, so that we can get the location of the
      * device. The result of the permission request is handled by a callback,
      * onRequestPermissionsResult.
      */
        if (context?.let {
                    ContextCompat.checkSelfPermission(it,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                }
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
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
                        mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                mLastKnownLocation?.latitude?.let {
                                    mLastKnownLocation?.longitude?.let { it1 ->
                                        LatLng(it, it1)
                                    }
                                }, DEFAULT_ZOOM.toFloat()))
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

    override fun onMapClick(latLng: LatLng) {

    }

    companion object {

        private val TAG = MapsFragment::class.java.getName()
        private val DEFAULT_ZOOM = 15
        val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

        fun newInstance(): MapsFragment {
            return MapsFragment()
        }
    }
}
