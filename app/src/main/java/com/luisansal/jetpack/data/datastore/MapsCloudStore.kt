package com.luisansal.jetpack.data.datastore

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import com.luisansal.jetpack.R
import com.luisansal.jetpack.data.Result
import com.luisansal.jetpack.data.mappers.DirectionMapper
import com.luisansal.jetpack.data.mappers.PlaceMapper
import com.luisansal.jetpack.data.network.request.MessageRequest
import com.luisansal.jetpack.domain.entity.Place
import com.luisansal.jetpack.domain.exceptions.RequestDirectionsDeniedException
import com.luisansal.jetpack.domain.exceptions.RequestPlacesDeniedException
import com.luisansal.jetpack.domain.network.ApiService
import com.luisansal.jetpack.domain.network.DirectionsApiService
import com.luisansal.jetpack.domain.network.PlacesApiService
import com.luisansal.jetpack.utils.ErrorUtil
import java.lang.Exception

class MapsCloudStore(
    private val apiService: ApiService,
    private val placesApiService: PlacesApiService,
    private val directionsApiService: DirectionsApiService,
    private val context: Context
) {

    suspend fun sendPosition(message: String, latiude: Double, longitude: Double): Result<Boolean> {
        try {
            val messageRequest = MessageRequest(message)
            val response = apiService.sendPosition(messageRequest, latiude, longitude)

            if (response.isSuccessful) {

                return Result.Success((response.body()?.statusCode == 200))
            }
            return ErrorUtil.result(response)
        } catch (e: Exception) {
            return ErrorUtil.result(e)
        }
    }

    suspend fun getPlaces(query: String, limit: Int = 6): Result<List<Place>> {
        try {
            val response = placesApiService.getPlaces(query, context.getString(R.string.google_maps_web_key))
            if (response.isSuccessful) {
                val body = response.body()!!
                if (body.status == "REQUEST_DENIED") {
                    return Result.Error(RequestPlacesDeniedException(body.error_message))
                }
                val places = PlaceMapper().map(body)
                return Result.Success(places.take(limit))
            }
            return ErrorUtil.result(response)
        } catch (e: Throwable) {
            return ErrorUtil.result(e)
        }
    }

    suspend fun getDirections(origin: String, destination: String): Result<List<LatLng>> {
        try {
            val response = directionsApiService.getDirections(origin, destination, context.getString(R.string.google_maps_web_key))
            if (response.isSuccessful) {
                val body = response.body()!!
                if (body.status == "REQUEST_DENIED") {
                    return Result.Error(RequestDirectionsDeniedException(body.error_message))
                }
                val directions = DirectionMapper().map(body)
                return Result.Success(directions)
            }
            return ErrorUtil.result(response)
        } catch (e: Throwable) {
            return ErrorUtil.result(e)
        }
    }

}
