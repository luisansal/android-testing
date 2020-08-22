package com.luisansal.jetpack.data.repository

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.luisansal.jetpack.data.database.BaseRoomDatabase
import com.luisansal.jetpack.domain.entity.User
import com.luisansal.jetpack.domain.entity.UserVisitJoin
import com.luisansal.jetpack.domain.entity.Visit
import java.util.ArrayList

class PopulateRepository(db : BaseRoomDatabase) {

    private val userVisitDao = db.userVisitDao()
    private val userDao = db.userDao()
    private val visitDao = db.visitDao()

    fun start(){
        if(userVisitDao.count() <= 0)
            userVisitDao.deleteAll()
        if(userDao.count() <= 0)
            userDao.deleteAll()

        var user = User()
        user.name = "Juan"
        user.lastName = "Alvarez"
        user.dni = "05159410"
        val userId = userDao.save(user)

        user = User()
        user.name = "Luis Alberto"
        user.lastName = "Sánchez Saldaña"
        user.dni = "70668281"
        userDao.save(user)

        val users = ArrayList<User>()
        for (i in 0..1000) {
            user = User()
            user.name = "User" + (i + 1)
            user.lastName = "Apell" + (i + 1)
            user.dni = "dni" + (i + 1)
            users.add(user)
        }
        userDao.saveAll(users)

        for (i in 0..1000) {
            val visit = Visit(location = LatLng(i*-2.0, i*3.0))
            val visitId = visitDao.save(visit)

            val userVisitJoin = UserVisitJoin(userId,visitId)
            userVisitDao.save(userVisitJoin)
        }

        Log.i("DB_ACTIONS", "Database Populated")

    }
}