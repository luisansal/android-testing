package com.luisansal.jetpack.data.network.response

import com.google.gson.annotations.SerializedName

data class StatusResponse(
        @SerializedName("status")
        val statusCode : Int = 0
)