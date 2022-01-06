package com.luisansal.jetpack.domain.dao

import com.luisansal.jetpack.domain.entity.UserVisitJoin
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.luisansal.jetpack.core.domain.entity.User
import com.luisansal.jetpack.domain.entity.Visit

@Dao
interface UserVisitJoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(userVisitJoin: UserVisitJoin) : Long

    @Query("DELETE FROM tbluser_tblvisit_join")
    fun deleteAll()

    @Query("select count(*) FROM tbluser_tblvisit_join")
    fun count() : Long

    @Query("DELETE FROM tbluser_tblvisit_join where userId = :userId")
    fun deleteAllByUser(userId :Long)

    @Query("""
               SELECT * FROM tbluser
               INNER JOIN tbluser_tblvisit_join ON tbluser_tblvisit_join.userId = tbluser.id
               WHERE tbluser_tblvisit_join.visitId=:visitId
               """)
    fun findUsersForVisit(visitId: Long): List<User>

    @Query("""
               SELECT * FROM tblvisit
               INNER JOIN tbluser_tblvisit_join ON tbluser_tblvisit_join.visitId = tblvisit.id
               WHERE tbluser_tblvisit_join.userId=:userId
               """)
    fun findVisitsForUser(userId: Long): List<Visit>
}
