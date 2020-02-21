package com.luisansal.jetpack

import com.luisansal.jetpack.ui.features.manageusers.mvp.ListUserFragmentMVP
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import com.luisansal.jetpack.data.database.MyRoomDatabase
import com.luisansal.jetpack.ui.features.manageusers.PagedUserAdapter
import org.junit.Rule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import com.luisansal.jetpack.domain.dao.UserDao
import org.junit.After


@RunWith(MockitoJUnitRunner::class)
class MainActivityTest
//: AndroidJUnitRunner()
{

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var mContext: Context

    @Mock
    lateinit var listUserFragmentPresenter: ListUserFragmentMVP.Presenter
//
//    lateinit var interactor: ListUserFragmentInteractor

    private lateinit var mDatabase: MyRoomDatabase

    private lateinit var userDao: UserDao

    @Before
    fun setup() {
        mContext = InstrumentationRegistry.getContext()
//        interactor = ListUserFragmentInteractor(listUserFragmentPresenter)
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

    fun <T> LiveData<T>.observeOnce(onChangeHandler: (T) -> Unit) {
        val observer = OneTimeObserver(handler = onChangeHandler)
        observe(observer, observer)
    }

    @Test

    fun obtenerListadoUsuarios() {

//        Mockito.`when`(listUserFragmentPresenter.context).thenReturn(mContext)

        val adapter = PagedUserAdapter()

        val users = LivePagedListBuilder(userDao.findAllUsersPaging(), 50).build()


//        adapter.submitList()

//        users.observeOnce {
//            adapter.submitList(it)
//        }


//        Mockito.`when`(listUserFragmentPresenter.adapterUsuarios).thenReturn(adapter)
//
//        interactor.setupLivePaged()
//        interactor.validarRvUsuariosPopulado()
//
//        Mockito.verify(listUserFragmentPresenter).rvUsuariosNoPopulado()
    }

//    fun <T> mockPagedList(list: List<T>): PagedList<T> {
//        val pagedList = Mockito.mock(PagedList::class.java) as PagedList<T>
//        Mockito.`when`(pagedList.get(ArgumentMatchers.anyInt())).then { invocation ->
//            val index = invocation.arguments.first() as Int
//            list[index]
//        }
//        Mockito.`when`(pagedList.size).thenReturn(list.size)
//        return pagedList
//    }

//    @Test
//    fun cantidadAdecuadaDePaginacion() {
//        Mockito.`when`(listUserFragmentPresenter.context).thenReturn(mContext)
//
//        interactor.setupLivePaged()
//        interactor.validarCantidadPaginacion(50)
//
//        Mockito.verify(listUserFragmentPresenter).cantidadValida()
//    }
}
