package com.luisansal.jetpack.data.network.response

import com.google.gson.annotations.SerializedName

data class DirectionsMapsResponse(
        @SerializedName("routes")
        val routes: List<ObjectRoutes> = emptyList(),
        @SerializedName("status")
        val status: String,
        @SerializedName("error_message")
        val error_message: String
)

data class ObjectRoutes(
        @SerializedName("legs")
        val legs: List<LegsObject>
)

data class LegsObject(
        @SerializedName("steps")
        val steps: List<StepsOject>
)

data class StepsOject(
        @SerializedName("start_location")
        val start_location: LocationObject,
        @SerializedName("end_location")
        val end_location: LocationObject
)