package com.luisansal.jetpack.domain.network

import com.luisansal.jetpack.data.network.response.PlacesMapsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import com.luisansal.jetpack.BuildConfig

interface MapsApiService {
    companion object {
        const val GMAPS_PLACES_URL: String = BuildConfig.GMAPS_PLACES_HOST
    }

    @GET("json")
    suspend fun getPlaces(@Query("query") query: String, @Query("key") key: String): Response<PlacesMapsResponse>

}