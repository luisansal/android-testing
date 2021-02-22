package com.luisansal.jetpack.data.datastore

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import com.luisansal.jetpack.R
import com.luisansal.jetpack.data.Result
import com.luisansal.jetpack.data.mappers.DirectionMapper
import com.luisansal.jetpack.data.mappers.PlaceMapper
import com.luisansal.jetpack.data.network.request.MessageRequest
import com.luisansal.jetpack.domain.entity.Place
import com.luisansal.jetpack.domain.exceptions.RequestDeniedException
import com.luisansal.jetpack.domain.exceptions.RequestDirectionsDeniedException
import com.luisansal.jetpack.domain.exceptions.RequestPlacesDeniedException
import com.luisansal.jetpack.domain.network.ApiService
import com.luisansal.jetpack.domain.network.DirectionsApiService
import com.luisansal.jetpack.domain.network.PlacesApiService
import com.luisansal.jetpack.utils.ErrorUtil
import java.lang.Exception

class MapsCloudStore(private val apiService: ApiService, private val placesApiService: PlacesApiService,private val directionsApiService: DirectionsApiService, private val context: Context) {

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
            val response = placesApiService.getPlaces(query, context.getString(R.string.google_maps_web_key))
            if (response.isSuccessful) {
                val body = response.body()
                if(body?.status == "REQUEST_DENIED"){
                    return Result.Error(ErrorUtil.handle(RequestPlacesDeniedException(body.error_message)))
                }
                val places = body?.let { PlaceMapper().map(it) }
                return Result.Success(places?.take(limit))
            }
            return Result.Error(ErrorUtil.handle(response.errorBody()))
        } catch (e: Throwable) {
            return Result.Error(ErrorUtil.handle(e))
        }
    }

    suspend fun getDirections(origin: String, destination: String): Result<List<LatLng>> {
        try {
            val response = directionsApiService.getDirections(origin,destination, context.getString(R.string.google_maps_web_key))
            if (response.isSuccessful) {
                val body = response.body()
                if(body?.status == "REQUEST_DENIED"){
                    return Result.Error(ErrorUtil.handle(RequestDirectionsDeniedException(body.error_message)))
                }
                val directions = body?.let { DirectionMapper().map(it) }
                return Result.Success(directions)
            }
            return Result.Error(ErrorUtil.handle(response.errorBody()))
        } catch (e: Throwable) {
            return Result.Error(ErrorUtil.handle(e))
        }
    }

}
