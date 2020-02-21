package com.luisansal.jetpack

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.test.core.app.ApplicationProvider
import com.luisansal.jetpack.domain.entity.User
import com.luisansal.jetpack.data.repository.UserRepository
import com.luisansal.jetpack.ui.features.manageusers.PagedUserAdapter
import com.luisansal.jetpack.ui.features.manageusers.mvp.ListUserFragmentInteractor
import com.luisansal.jetpack.ui.features.manageusers.mvp.ListUserFragmentMVP
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Thread.sleep


@RunWith(MockitoJUnitRunner::class)
class LiveDataInteractorTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var listUserFragmentPresenter: ListUserFragmentMVP.Presenter

    lateinit var mContext: Context

    lateinit var interactor: ListUserFragmentInteractor

//    private lateinit var userDao: UserDao

    @Before
    fun setup() {
        interactor = ListUserFragmentInteractor(listUserFragmentPresenter)
        mContext = ApplicationProvider.getApplicationContext<Context>()

//        val db = Room.inMemoryDatabaseBuilder(
//                mContext, MyRoomDatabase::class.java).build()
//        userDao = db.userDao()
    }

//    @After
//    @Throws(IOException::class)
//    fun closeDb() {
//        db.close()
//    }

    fun <T> LiveData<T>.observeOnce(onChangeHandler: (T) -> Unit) {
        val observer = OneTimeObserver(handler = onChangeHandler)
        observe(observer, observer)
    }

    @Test
    fun validarRvUsuariosPopulado() {
        val user = User()
        user.name = "Luis"
        user.dni = "70668281"

        UserRepository.newInstance(mContext).save(user)
        val allUsers = LivePagedListBuilder(UserRepository.newInstance(mContext).allUsersPaging, 50).build()

        val adapter = PagedUserAdapter()

        allUsers.observeOnce {
            adapter.submitList(it)
            sleep(30)
        }

        Mockito.`when`(listUserFragmentPresenter.adapterUsuarios).thenReturn(adapter)

        interactor.validarRvUsuariosPopulado()
        verify(listUserFragmentPresenter).rvUsuariosPopulado()
    }

    @Test
    fun validarCantidadUsuarios() {
        Mockito.`when`(listUserFragmentPresenter.context).thenReturn(mContext)

        val allUsers = LivePagedListBuilder(UserRepository.newInstance(mContext).allUsersPaging, 50).build()

        val adapter = PagedUserAdapter()

        allUsers.observeOnce {
            adapter.submitList(it)
            sleep(30)
        }

        sleep(1500)

        Mockito.`when`(listUserFragmentPresenter.numUsers).thenReturn(adapter.itemCount)

        interactor.validarCantidadPaginacion(1001)

        verify(listUserFragmentPresenter).cantidadValida()
    }

}
