package com.luisansal.jetpack.data.repository

import com.luisansal.jetpack.domain.dao.UserDao
import com.luisansal.jetpack.data.database.BaseRoomDatabase
import com.luisansal.jetpack.domain.entity.User

import androidx.paging.DataSource

class UserRepository(db: BaseRoomDatabase) {

    private val userDao : UserDao = db.userDao()

    val allUsers: List<User>
        get() = userDao.findAllUsers()

    val allUsersInline: List<User>
        get() = userDao.findAllUsersInline()

    val allUsersPaging: DataSource.Factory<Int, User>
        get() = userDao.findAllUsersPaging()


    fun save(user: User) {
        userDao.save(user)
    }

    fun getUserByDni(dni: String): User? {
        return userDao.findOneByDni(dni)
    }

    fun getUserById(id: Long): User? {
        return userDao.findOneById(id)
    }

}
