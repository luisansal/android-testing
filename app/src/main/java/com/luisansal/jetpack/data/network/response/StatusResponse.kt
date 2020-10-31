package com.luisansal.jetpack.data.network.response

import com.google.gson.annotations.SerializedName

data class StatusResponse(
        @SerializedName("statusCode")
        val statusCode: Int = 0,
        @SerializedName("info")
        val info: String = "",
        @SerializedName("error")
        val error: String = ""
)