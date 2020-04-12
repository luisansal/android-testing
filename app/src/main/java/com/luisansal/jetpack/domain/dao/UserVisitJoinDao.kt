package com.luisansal.jetpack.domain.dao

import com.luisansal.jetpack.domain.entity.UserVisitJoin
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.luisansal.jetpack.domain.entity.User
import com.luisansal.jetpack.domain.entity.Visit

@Dao
interface UserVisitJoinDao {
    @Insert
    fun save(userVisitJoin: UserVisitJoin)

    @Query("DELETE FROM tbluser_tblvisit_join")
    fun deleteAll()

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
