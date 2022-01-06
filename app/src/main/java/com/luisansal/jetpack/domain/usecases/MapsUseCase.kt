package com.luisansal.jetpack.domain.usecases

import com.google.android.gms.maps.model.LatLng
import com.luisansal.jetpack.core.data.Result
import com.luisansal.jetpack.data.datastore.MapsCloudStore
import com.luisansal.jetpack.data.repository.MapsRepository
import com.luisansal.jetpack.domain.entity.Place

class MapsUseCase(private val mapsRespository : MapsRepository,private val mapsCloudStore: MapsCloudStore) {
    suspend fun sendPosition(message: String, latiude: Double, longitude: Double): Result<Boolean> {
        return mapsRespository.sendPosition(message,latiude,longitude)
    }
    suspend fun getPlaces(query: String): Result<List<Place>> {
        return mapsCloudStore.getPlaces(query)
    }
    suspend fun getDirections(origin: String,destination: String): Result<List<LatLng>> {
        return mapsCloudStore.getDirections(origin,destination)
    }
}
