package com.luisansal.jetpack.domain.dao

import com.luisansal.jetpack.domain.entity.Visit

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VisitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(visit: Visit)

    @Query("DELETE FROM tblvisit")
    fun deleteAll()

    @Query("SELECT * from tblvisit ORDER BY userId ASC")
    fun findAll(): LiveData<List<Visit>>

    @Query("SELECT * from tblvisit where id = :id")
    fun findOneById(id: Long?): LiveData<Visit>
}
