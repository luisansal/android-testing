package com.luisansal.jetpack.data.repository

import com.luisansal.jetpack.data.database.BaseRoomDatabase
import com.luisansal.jetpack.domain.entity.UserVisitJoin
import com.luisansal.jetpack.domain.entity.Visit

class VisitRepository(db: BaseRoomDatabase) {

    private val userVisitDao = db.userVisitDao()
    private val userDao = db.userDao()
    private val visitDao = db.visitDao()

    fun getByUser(dni: String): List<Visit>? {
        val user = userDao.findOneByDni(dni);
        return user?.id?.let { userVisitDao.findVisitsForUser(it) }
    }

    fun saveUser(visitId: Long, userId: Long): Boolean {
        val userVisitJoin = UserVisitJoin(userId, visitId)
        return userVisitDao.save(userVisitJoin) > 0
    }

    fun deleteAllByUser(userId: Long) {
        userVisitDao.deleteAllByUser(userId)
    }

    fun deleteAll() {
        userVisitDao.deleteAll()
    }

    fun save(visit: Visit): Long {
        return visitDao.save(visit)
    }
}
