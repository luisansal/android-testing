package com.luisansal.jetpack.data.mappers

import com.google.android.gms.maps.model.LatLng
import com.luisansal.jetpack.data.network.response.PlacesMapsResponse
import com.luisansal.jetpack.domain.entity.Place

class PlaceMapper {
    fun map(response: PlacesMapsResponse): List<Place> {
        return response.results.map {
            val latLng = LatLng(it.geometry.location.lat, it.geometry.location.lng)
            var compoundCode = ""
            it.plus_code?.let { plus_code ->
                plus_code.compound_code.split(" ").forEachIndexed { index, s ->
                    if (index != 0)
                        compoundCode += " $s"
                }
            }
            Place(
                id = it.place_id,
                formattedAddress = it.formatted_address,
                latLng = latLng,
                district = compoundCode,
                icon = it.icon,
                name = it.name
            )
        }
    }
}