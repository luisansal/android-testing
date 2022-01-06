package com.luisansal.jetpack.core.data.network.response

import com.google.gson.annotations.SerializedName

data class StatusResponse(
        @SerializedName("statusCode")
        val statusCode: Int = 0,
        @SerializedName("message")
        val message: String = ""
)