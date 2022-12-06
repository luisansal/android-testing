package com.luisansal.jetpack.data.database

import android.content.Context
import android.os.AsyncTask
import com.luisansal.jetpack.domain.dao.UserDao
import com.luisansal.jetpack.domain.dao.UserVisitJoinDao
import com.luisansal.jetpack.domain.dao.VisitDao
import com.luisansal.jetpack.core.domain.entities.User
import com.luisansal.jetpack.domain.entity.Visit
import com.luisansal.jetpack.domain.converter.LatLngConverter
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.luisansal.jetpack.domain.entity.UserVisitJoin

@Database(entities = [User::class, Visit::class, UserVisitJoin::class], version = 7)
@TypeConverters(LatLngConverter::class)
abstract class BaseRoomDatabase : RoomDatabase() {

    var isTest = false

    abstract fun userDao(): UserDao

    abstract fun visitDao(): VisitDao

    abstract fun userVisitDao(): UserVisitJoinDao

    private class PopulateDbAsync(baseRoomDatabase: BaseRoomDatabase) : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {

            return null
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: BaseRoomDatabase? = null

        fun getDatabase(context: Context): BaseRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(BaseRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder<BaseRoomDatabase>(context, BaseRoomDatabase::class.java, "myDatabase")
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
                if (!INSTANCE?.isTest!!)
                    PopulateDbAsync(INSTANCE!!).execute()
            }
        }
    }
}