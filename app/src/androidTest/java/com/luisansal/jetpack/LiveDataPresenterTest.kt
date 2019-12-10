package com.luisansal.jetpack

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.rule.ActivityTestRule
import com.luisansal.jetpack.data.repository.UserRepository
import com.luisansal.jetpack.ui.features.manageusers.mvp.ListUserFragmentMVP
import com.luisansal.jetpack.ui.features.manageusers.mvp.ListUserFragmentPresenter
import com.luisansal.jetpack.ui.features.manageusers.RoomViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class LiveDataPresenterTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mActivity = ActivityTestRule(MainActivity::class.java)

    @Mock
    lateinit var listUserFragmentView: ListUserFragmentMVP.View

    @Mock
    lateinit var listUserFragmentInteractor: ListUserFragmentMVP.Interactor

    lateinit var mContext: Context

    lateinit var presenter: ListUserFragmentPresenter

    @Before
    fun setup() {
        presenter = ListUserFragmentPresenter(listUserFragmentView)
        mContext = ApplicationProvider.getApplicationContext<Context>()
    }

    fun <T> LiveData<T>.observeOnce(onChangeHandler: (T) -> Unit) {
        val observer = OneTimeObserver(handler = onChangeHandler)
        observe(observer, observer)
    }

    @Test
    @Throws(Exception::class)
    fun validarRvUsuariosPopulado() {

        val allUsers = LivePagedListBuilder(UserRepository.newInstance(mContext).allUsersPaging, 50).build()

//        Mockito.`when`(listUserFragmentView.rvUsers).thenReturn(RecyclerView(mContext))

        val lifecycle = LifecycleRegistry(mock(LifecycleOwner::class.java))
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        Mockito.`when`(listUserFragmentView.lifecycle).thenReturn(lifecycle)

        val roomViewModel = ViewModelProviders.of(mActivity.activity).get(RoomViewModel::class.java)
        Mockito.`when`(listUserFragmentView.roomViewModel).thenReturn(roomViewModel)


        presenter.setupRv()
        presenter.setupRoomViewModel()

        presenter.populateRoomViewModel(allUsers)
        presenter.populateAdapterRv(allUsers)

        Thread.sleep(1000)

        presenter.validarRvUsuariosPopulado()

        verify(listUserFragmentView).rvUsuariosPopulado()
    }
}
