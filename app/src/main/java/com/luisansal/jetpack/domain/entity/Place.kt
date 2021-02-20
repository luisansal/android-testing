package com.luisansal.jetpack.domain.entity

import com.google.android.gms.maps.model.LatLng

class Place(
    var id: String,
    var formattedAddress: String,
    var latLng: LatLng,
    var district: String,
    var icon: String,
    var name: String
)