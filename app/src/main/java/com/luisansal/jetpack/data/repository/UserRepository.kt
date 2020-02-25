package com.luisansal.jetpack.data.repository

import android.content.Context

import com.luisansal.jetpack.domain.dao.UserDao
import com.luisansal.jetpack.data.database.MyRoomDatabase
import com.luisansal.jetpack.domain.entity.User

import androidx.lifecycle.LiveData
import androidx.paging.DataSource

class UserRepository(mContext: Context) {

    val allUsers: LiveData<List<User>>
        get() = mUserDaoInstance!!.findAllUsers()

    val allUsersInline: List<User>
        get() = mUserDaoInstance!!.findAllUsersInline()

    val allUsersPaging: DataSource.Factory<Int, User>
        get() = mUserDaoInstance!!.findAllUsersPaging()

    init {
        val db = MyRoomDatabase.getDatabase(mContext)
        if (mUserDaoInstance == null) {
            mUserDaoInstance = db!!.userDao()
        }
    }

    fun save(user: User)  {
        mUserDaoInstance!!.save(user)
    }

    fun getUserByDni(dni: String): LiveData<User?> {
        return mUserDaoInstance!!.findOneByDni(dni)
    }

    fun getUserById(id: Long): LiveData<User?> {
        return mUserDaoInstance!!.findOneById(id)
    }

    companion object {
        private var mUserDaoInstance: UserDao? = null

        fun newInstance(mContext: Context): UserRepository {
            return UserRepository(mContext)
        }
    }
}
