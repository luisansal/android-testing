package com.luisansal.jetpack.domain.usecases

import com.luisansal.jetpack.data.Result
import com.luisansal.jetpack.data.repository.MapsRepository

class MapsUseCase(private val mapsRespository : MapsRepository) {
    suspend fun sendPosition(message: String, latiude: Double, longitude: Double): Result<Boolean> {
        return mapsRespository.sendPosition(message,latiude,longitude)
    }
}
