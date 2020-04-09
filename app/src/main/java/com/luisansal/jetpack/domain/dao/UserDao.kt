package com.luisansal.jetpack.domain.dao

import com.luisansal.jetpack.domain.entity.User

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(user: User): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(users: List<User>)

    @Query("DELETE FROM tbluser")
    fun deleteAll()

    @Query("SELECT * from tbluser ORDER BY name ASC")
    fun findAllUsers(): List<User>

    @Query("SELECT * from tbluser ORDER BY name ASC")
    fun findAllUsersInline(): List<User>

    // The Integer type parameter tells Room to use a PositionalDataSource
    // object, with position-based loading under the hood.
    @Query("SELECT * FROM tbluser ORDER BY name asc")
    fun findAllUsersPaging(): DataSource.Factory<Int, User>

    @Query("SELECT * from tbluser where dni = :dni")
    fun findOneByDni(dni: String): User?

    @Query("SELECT * from tbluser where id = :id")
    fun findOneById(id: Long): User?

    @Query("DELETE FROM tbluser where dni = :dni")
    fun deleteUser(dni: String): Int

    @Query("DELETE FROM tbluser")
    fun deleteAllUser(): Int

}
