package com.luisansal.jetpack

import com.luisansal.jetpack.ui.features.manageusers.mvp.ListUserFragmentInteractor
import com.luisansal.jetpack.ui.features.manageusers.mvp.ListUserFragmentMVP
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagedList
import com.luisansal.jetpack.data.database.MyRoomDatabase
import org.junit.Rule
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import androidx.room.Room
import com.luisansal.jetpack.domain.dao.UserDao
import org.junit.After
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainActivityTest2
//: AndroidJUnitRunner()
{

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mContext: Context

    @Mock
    lateinit var listUserFragmentPresenter: ListUserFragmentMVP.Presenter

    lateinit var interactor: ListUserFragmentInteractor

    private lateinit var mDatabase: MyRoomDatabase

    private lateinit var userDao: UserDao

    @Mock
    lateinit var context : Context

    @Before
    fun setup() {
        interactor = ListUserFragmentInteractor(context,listUserFragmentPresenter)
        mDatabase = Room.inMemoryDatabaseBuilder(mContext,
                MyRoomDatabase::class.java!!)
                .allowMainThreadQueries()
                .build()

        userDao = mDatabase.userDao()
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        mDatabase.close()
    }

    @Test
    fun obtenerListadoUsuarios() {

        Mockito.`when`(context).thenReturn(mContext)

//        val adapter = PagedUserAdapter()
//        val pagedList = PagedList<User>()
//
//        adapter.submitList()
//        Mockito.`when`(listUserFragmentPresenter.adapterUsuarios).thenReturn()



//        Mockito.`when`(listUserFragmentPresenter.adapterUsuarios).thenReturn(adapter)

        interactor.setupLivePaged()
        interactor.validarRvUsuariosPopulado()

        Mockito.verify(listUserFragmentPresenter).rvUsuariosNoPopulado()
    }

    fun <T> mockPagedList(list: List<T>): PagedList<T> {
        val pagedList = Mockito.mock(PagedList::class.java) as PagedList<T>
        Mockito.`when`(pagedList.get(ArgumentMatchers.anyInt())).then { invocation ->
            val index = invocation.arguments.first() as Int
            list[index]
        }
        Mockito.`when`(pagedList.size).thenReturn(list.size)
        return pagedList
    }

    @Test
    fun cantidadAdecuadaDePaginacion() {

        interactor.setupLivePaged()
        interactor.validarCantidadPaginacion(50)

        Mockito.verify(listUserFragmentPresenter).cantidadValida()
    }
}
