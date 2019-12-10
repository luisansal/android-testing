package com.luisansal.jetpack.data.database

import android.content.Context
import android.os.AsyncTask
import android.util.Log

import com.google.android.gms.maps.model.LatLng
import com.luisansal.jetpack.domain.dao.UserDao
import com.luisansal.jetpack.domain.dao.UserVisitsDao
import com.luisansal.jetpack.domain.dao.VisitDao
import com.luisansal.jetpack.domain.entity.User
import com.luisansal.jetpack.domain.entity.Visit
import com.luisansal.jetpack.domain.converter.LatLngConverter

import java.util.ArrayList
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [User::class, Visit::class], version = 4)
@TypeConverters(LatLngConverter::class)
abstract class MyRoomDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun visitDao(): VisitDao

    abstract fun userVisitsDao(): UserVisitsDao

    private class PopulateDbAsync (myRoomDatabase: MyRoomDatabase) : AsyncTask<Void, Void, Void>() {

        private val userDao: UserDao
        private val visitDao: VisitDao

        init {
            userDao = myRoomDatabase.userDao()
            visitDao = myRoomDatabase.visitDao()
        }

        override fun doInBackground(vararg voids: Void): Void? {
            userDao.deleteAll()

            var user = User()
            user.name = "Juan"
            user.lastName = "Alvarez"
            user.dni = "05159410"
            val lastUserId = userDao.save(user)

            val visit = Visit(LatLng(-35.0, 151.0), lastUserId)

            visitDao.save(visit)

            val users = ArrayList<User>()
            for (i in 0..999) {
                user = User()
                user.name = "User" + (i + 1)
                user.lastName = "Apell" + (i + 1)
                user.dni = "dni" + (i + 1)
                users.add(user)
            }
            userDao.saveAll(users)

            Log.i("DB_ACTIONS", "Database Populated")

            return null
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: MyRoomDatabase? = null

        fun getDatabase(context: Context): MyRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(MyRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder<MyRoomDatabase>(context, MyRoomDatabase::class.java, "myDatabase")
                                .fallbackToDestructiveMigration()
                                .allowMainThreadQueries()
                                .addCallback(sRoomDatabaseCallback)
                                .build()
                    }
                }
            }
            return INSTANCE
        }

        private val sRoomDatabaseCallback = object : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                PopulateDbAsync(INSTANCE!!).execute()
            }
        }
    }
}