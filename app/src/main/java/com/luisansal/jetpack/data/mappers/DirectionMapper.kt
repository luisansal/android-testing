package com.luisansal.jetpack.data.mappers

import com.google.android.gms.maps.model.LatLng
import com.luisansal.jetpack.data.network.response.DirectionsMapsResponse

class DirectionMapper {
    fun map(response: DirectionsMapsResponse): List<LatLng> {
        return response.routes.map {
            return it.legs[0].steps.map { step ->
                LatLng(step.start_location.lat, step.start_location.lng)
            }
        }
    }
}