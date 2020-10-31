package com.luisansal.jetpack.data.network.request

import com.google.gson.annotations.SerializedName

data class MessageRequest(
        @SerializedName("message")
        val message: String
)