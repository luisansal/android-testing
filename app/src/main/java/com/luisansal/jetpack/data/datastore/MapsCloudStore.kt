package com.luisansal.jetpack.data.datastore

import android.content.Context
import com.luisansal.jetpack.R
import com.luisansal.jetpack.data.Result
import com.luisansal.jetpack.data.mappers.PlaceMapper
import com.luisansal.jetpack.data.network.request.MessageRequest
import com.luisansal.jetpack.domain.entity.Place
import com.luisansal.jetpack.domain.network.ApiService
import com.luisansal.jetpack.domain.network.MapsApiService
import com.luisansal.jetpack.utils.ErrorUtil
import java.lang.Exception

class MapsCloudStore(private val apiService: ApiService, private val mapsApiService: MapsApiService,private val context: Context) {

    suspend fun sendPosition(message: String, latiude: Double, longitude: Double): Result<Boolean> {
        try {
            val messageRequest = MessageRequest(message)
            val response = apiService.sendPosition(messageRequest, latiude, longitude)

            if (response.isSuccessful) {

                return Result.Success((response.body()?.statusCode == 200))
            }
            return Result.Error(ErrorUtil.handle(response.errorBody()))
        } catch (e: Exception) {
            return Result.Error(ErrorUtil.handle(e))
        }
    }

    suspend fun getPlaces(query: String, limit : Int = 6): Result<List<Place>> {
        try {
            val response = mapsApiService.getPlaces(query, context.getString(R.string.google_maps_web_key))
            if (response.isSuccessful) {
                val body = response.body()
                val places = body?.let { PlaceMapper().map(it) }
                return Result.Success(places?.take(limit))
            }
            return Result.Error(ErrorUtil.handle(response.errorBody()))
        } catch (e: Throwable) {
            return Result.Error(ErrorUtil.handle(e))
        }
    }

}
