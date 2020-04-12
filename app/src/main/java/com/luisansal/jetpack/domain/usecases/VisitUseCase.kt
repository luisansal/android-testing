package com.luisansal.jetpack.domain.usecases

import com.luisansal.jetpack.data.repository.VisitRepository
import com.luisansal.jetpack.domain.entity.Visit

class VisitUseCase(private val visitRepository: VisitRepository) {

    fun getByUser(dni: String): List<Visit>? {
        return visitRepository.getByUser(dni)
    }

    fun saveOneVisitForUser(visit: Visit, userId: Long): Boolean {
        val visitId = visitRepository.save(visit);
        return visitRepository.saveOneVisitForUser(visitId, userId)
    }
}