package com.luisansal.jetpack.ui.features.maps

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

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

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.GeoDataClient
import com.google.android.gms.location.places.PlaceDetectionClient
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.luisansal.jetpack.R
import com.luisansal.jetpack.common.interfaces.TitleListener
import com.luisansal.jetpack.data.repository.UserVisitsRepository
import com.luisansal.jetpack.ui.utils.injectFragment
import kotlinx.android.synthetic.main.maps_fragment.*
import org.koin.android.ext.android.inject

class MapsFragment : Fragment(), OnMapReadyCallback, TitleListener, GoogleMap.OnMapClickListener {
    private var mViewModel: MapsViewModel? = null
    override val title = "Maps Manager"

    private var mGoogleMap: GoogleMap? = null

    private var mLocationPermissionGranted: Boolean = false
    private val mDefaultLocation = LatLng(-33.8523341, 151.2106085)
    private var mLastKnownLocation: Location? = null
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private var mPlaceDetectionClient: PlaceDetectionClient? = null
    private var mGeoDataClient: GeoDataClient? = null
    private val userVisitsRepository: UserVisitsRepository by injectFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(activity!!.applicationContext, null)

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(activity!!.applicationContext, null)

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity!!.applicationContext)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.maps_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        mapFragment!!.getMapAsync(this)
        onClickBtnMostrarVisitas()
        onACBuscarLugaresTextChanged()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(MapsViewModel::class.java!!)
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
        //        LatLng sydney = new LatLng(-34, 151);
        //        mGoogleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    private fun mostrarvisitas() {

        userVisitsRepository.getUserAllVisitsByDni("05159410").observe(this, Observer { userAndAllVists ->
            for (visit in userAndAllVists.visits) {
                mGoogleMap!!.addMarker(MarkerOptions().position(visit.location).title("Marker user: " + userAndAllVists.user.name!!))
                mGoogleMap!!.moveCamera(CameraUpdateFactory.newLatLng(visit.location))
            }
        })
    }

    fun onClickBtnMostrarVisitas() {
        btnMostrarVisitas.setOnClickListener { mostrarvisitas() }
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
        if (ContextCompat.checkSelfPermission(activity!!.applicationContext,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(activity!!,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
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
                mGoogleMap!!.isMyLocationEnabled = true
                mGoogleMap!!.uiSettings.isMyLocationButtonEnabled = true
            } else {
                mGoogleMap!!.isMyLocationEnabled = false
                mGoogleMap!!.uiSettings.isMyLocationButtonEnabled = false
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
                val locationResult = mFusedLocationProviderClient!!.lastLocation
//                locationResult.addOnCompleteListener(activity!!, object : OnCompleteListener {
//                    override fun onComplete(task: Task<*>) {
//                        if (task.isSuccessful) {
//                            // Set the map's camera position to the current location of the device.
//                            mLastKnownLocation = task.result as Location?
//                            mGoogleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(
//                                    LatLng(mLastKnownLocation!!.latitude,
//                                            mLastKnownLocation!!.longitude), DEFAULT_ZOOM.toFloat()))
//                        } else {
//                            Log.d(TAG, "Current location is null. Using defaults.")
//                            Log.e(TAG, "Exception: %s", task.exception)
//                            mGoogleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM.toFloat()))
//                            mGoogleMap!!.uiSettings.isMyLocationButtonEnabled = false
//                        }
//                    }
//                })
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message)
        }

    }

    override fun onMapClick(latLng: LatLng) {

    }

    companion object {

        private val TAG = MapsFragment::class.java!!.getName()
        private val DEFAULT_ZOOM = 15
        val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

        fun newInstance(): MapsFragment {
            return MapsFragment()
        }
    }
}
