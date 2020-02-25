package com.luisansal.jetpack

import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.*
import androidx.room.Room
import com.luisansal.jetpack.domain.dao.UserDao
import com.luisansal.jetpack.data.database.MyRoomDatabase
import com.luisansal.jetpack.utils.OneTimeObserver
import com.luisansal.jetpack.utils.observeOnce
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner


inline fun <reified T> lambdaMock(): T = mock(T::class.java)

@RunWith(MockitoJUnitRunner::class)
class LiveDataTest2 {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mContext: Context

    private lateinit var mDatabase: MyRoomDatabase

    private lateinit var userDao: UserDao

    @Before
    fun setup() {
//        val context = ApplicationProvider.getApplicationContext<Context>()

        mDatabase = Room.inMemoryDatabaseBuilder(mContext,
                MyRoomDatabase::class.java!!)
                .build()

        userDao = mDatabase.userDao()
    }

    @Test
    fun showUsers() {
        val allUsers = userDao.findAllUsers()

        allUsers.observeOnce {
            Log.d("num",it.size.toString())
        }

//        verify(observer).invoke("title")
    }


    @After
    @Throws(Exception::class)
    fun closeDb() {
        mDatabase.close()
    }
}