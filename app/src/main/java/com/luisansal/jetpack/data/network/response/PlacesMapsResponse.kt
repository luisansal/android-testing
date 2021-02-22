package com.luisansal.jetpack.data.network.response

import com.google.gson.annotations.SerializedName

data class PlacesMapsResponse(
        @SerializedName("results")
        val results: List<ObjectResult> = emptyList(),
        @SerializedName("status")
        val status: String,
        @SerializedName("error_message")
        val error_message: String
)

data class ObjectResult(
        @SerializedName("formatted_address")
        val formatted_address: String,
        @SerializedName("geometry")
        val geometry: GeometryOject,
        @SerializedName("icon")
        val icon: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("place_id")
        val place_id: String,
        @SerializedName("plus_code")
        val plus_code: PlusCodeObject?,
        @SerializedName("reference")
        val reference: String
)

data class PlusCodeObject(
        @SerializedName("compound_code")
        val compound_code: String,
        @SerializedName("global_code")
        val global_code: String
)

data class GeometryOject(
        @SerializedName("location")
        val location: LocationObject
)

data class LocationObject(
        @SerializedName("lat")
        val lat: Double = 0.0,
        @SerializedName("lng")
        val lng: Double = 0.0
)