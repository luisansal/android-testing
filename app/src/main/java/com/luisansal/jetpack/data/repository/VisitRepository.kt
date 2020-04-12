package com.luisansal.jetpack.data.repository

import com.luisansal.jetpack.data.database.BaseRoomDatabase
import com.luisansal.jetpack.domain.entity.Visit

class VisitRepository(db : BaseRoomDatabase) {

    private val mDao = db.userVisitDao()
    private val userDao = db.userDao()

    fun getByUser(dni: String): List<Visit>? {
        val user = userDao.findOneByDni(dni);
        return user?.id?.let { mDao.findVisitsForUser(it) }
    }

}
