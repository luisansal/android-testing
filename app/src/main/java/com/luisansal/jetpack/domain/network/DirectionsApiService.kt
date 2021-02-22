package com.luisansal.jetpack.domain.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import com.luisansal.jetpack.BuildConfig
import com.luisansal.jetpack.data.network.response.DirectionsMapsResponse

interface DirectionsApiService {
    companion object {
        const val GMAPS_URL: String = BuildConfig.GMAPS_DIRECTIONS_HOST
    }

    @GET("json")
    suspend fun getDirections(@Query("origin") origin: String, @Query("destination") destination: String, @Query("key") key: String)
             : Response<DirectionsMapsResponse>

}