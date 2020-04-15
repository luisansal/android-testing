package com.luisansal.jetpack.domain.usecases

import com.luisansal.jetpack.data.repository.PopulateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PopulateUseCase(private val populateRepository: PopulateRepository) {

    suspend fun start(): Boolean = withContext(Dispatchers.Default){
        populateRepository.start()
        true
    }

}