package com.luisansal.jetpack.features.maps

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.luisansal.jetpack.R
import com.luisansal.jetpack.base.BaseActivity
import com.luisansal.jetpack.features.TempData
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.util.*

class MapsActivity : BaseActivity(), OnMapReadyCallback {
    companion object {
        private val TAG = MapsActivity::class.java.name
        private const val DEFAULT_ZOOM = 18
        private const val LOCATION = "LOCATION"

        fun newInstance(context: Context,location: String): Intent {
            val intent = Intent(context, MapsActivity::class.java)
            intent.putExtra(LOCATION,location)
            return intent
        }
    }

    private val locationStr by lazy { intent.getStringExtra(LOCATION)  }
    override fun getViewIdResource() = R.layout.activity_maps
    private lateinit var mGoogleMap: GoogleMap
    private var mLastKnownLocation: Location? = null
    private val mFusedLocationProviderClient by lazy { LocationServices.getFusedLocationProviderClient(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initMap(savedInstanceState)
        tvToolbarTile?.text = getString(R.string.select_location)
        onClickBtnBack()
        onClickConfirm()
    }

    private fun initMap(savedInstanceState: Bundle?) {
        val mapView = wrapMap as MapView?
        mapView?.onCreate(savedInstanceState)
        mapView?.onResume()
        mapView?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap

        updateLocationUI()

        getDeviceLocation()

        //onClickMap()
        onMoveMap()
    }

    private fun updateLocationUI() {
        if (mGoogleMap == null) {
            return
        }
        try {
            mGoogleMap?.isMyLocationEnabled = true
            mGoogleMap?.uiSettings?.isMyLocationButtonEnabled = true
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message ?: "")
        }
    }

    private fun getDeviceLocation() {
        try {
            requestLocation()
            mFusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                mLastKnownLocation = location
                mLastKnownLocation?.latitude?.let {
                    mLastKnownLocation?.longitude?.let { it1 ->
                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it, it1), DEFAULT_ZOOM.toFloat()))
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message ?: "")
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        val mLocationRequest: LocationRequest = LocationRequest.create()
        mLocationRequest.numUpdates = 1
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val mLocationCallback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) = Unit
        }
        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null)
    }

    private fun onMoveMap() {
        mGoogleMap.setOnCameraIdleListener {
            val location: LatLng = mGoogleMap.cameraPosition.target
            Log.d("loation", location.toString())
            val gc = Geocoder(this, Locale.getDefault())
            val addresses = gc.getFromLocation(location.latitude, location.longitude, 1)
            if (addresses.size == 1) {
                val addressText = addresses[0].getAddressLine(0)
                val addressFormated = getAddressFormat(addressText)
                if(locationStr == MapsFragment.LOCATION_ORIGIN){
                    TempData.address = addressFormated
                    TempData.addressLocation = location
                }
                if(locationStr == MapsFragment.LOCATION_DESTINATION){
                    TempData.addressDestination = addressFormated
                    TempData.addressLocationDestination = location
                }
                TempData.lastLocation = locationStr

                gpToolTip.visibility = View.VISIBLE
                tvTextToolTip.text = addressFormated
            }
        }
        mGoogleMap.setOnCameraMoveListener {
            gpToolTip.visibility = View.GONE
            tvTextToolTip.setText("")
        }
    }

    private fun getAddressFormat(addressText: String): String {
        var finalText = ""
        addressText.split(",").forEachIndexed { index, s ->
            if (index != 1 && index != 2)
                finalText += "$s "
        }
        return finalText
    }

    private fun onClickBtnBack() {
        ivToolbar?.setOnClickListener {
            onBackPressed()
        }
    }

    private fun onClickConfirm() {
        btnConfirm?.setOnClickListener {
            finish()
        }
    }
}