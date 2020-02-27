package com.luisansal.jetpack.data.repository

import com.luisansal.jetpack.data.database.BaseRoomDatabase
import com.luisansal.jetpack.domain.entity.UserAndAllVists

import androidx.lifecycle.LiveData

class UserVisitsRepository(db : BaseRoomDatabase) {

    private val mDao = db.userVisitsDao()

    fun getUserAllVisitsById(userId: Long?): LiveData<UserAndAllVists> {
        return mDao.findUserAllVisitsById(userId)
    }

    fun getUserAllVisitsByDni(dni: String): LiveData<UserAndAllVists> {
        return mDao.findUserAllVisitsByDni(dni)
    }

    //    public void save(UserAndAllVists user) {
    //        mUserDaoInstance.save(user);
    //    }

}
