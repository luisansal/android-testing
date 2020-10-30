package com.luisansal.jetpack.data.network.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
        @SerializedName("access_token")
        val accessToken: String,
        @SerializedName("token_type")
        val tokenType: String,
        @SerializedName("expires_in")
        val expiresIn: Long,
        @SerializedName("user")
        val user: UserResponse
) {
    data class UserResponse(
            @SerializedName("id")
            val id: Long,
            @SerializedName("names")
            val names: String,
            @SerializedName("lastnames")
            val lastNames: String,
            @SerializedName("dni")
            val dni: String,
            @SerializedName("fcbkId")
            val fcbkId: String?,
            @SerializedName("address")
            val address: String,
            @SerializedName("email")
            val email: String,
            @SerializedName("emailConfirmation")
            val emailConfirmation: Boolean,
            @SerializedName("email_verified_at")
            val emailVerifiedAt: Long,
            @SerializedName("filePath")
            val filePath: String?,
            @SerializedName("created_at")
            val createdAt: Long,
            @SerializedName("updated_at")
            val updatedAt: Long?
    )
}

