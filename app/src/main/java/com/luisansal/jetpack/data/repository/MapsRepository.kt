package com.luisansal.jetpack.data.repository

import com.luisansal.jetpack.data.Result
import com.luisansal.jetpack.data.datastore.MapsCloudStore

class MapsRepository(private val mapsCloudStore: MapsCloudStore) {

    suspend fun sendPosition(message: String, latitude: Double, longitude: Double): Result<Boolean> {
        return mapsCloudStore.sendPosition(message,latitude, longitude)
    }
}
