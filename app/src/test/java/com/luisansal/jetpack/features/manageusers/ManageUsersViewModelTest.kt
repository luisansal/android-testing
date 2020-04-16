package com.luisansal.jetpack.features.manageusers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.luisansal.jetpack.base.TestCoroutineRule
import com.luisansal.jetpack.domain.entity.User
import com.luisansal.jetpack.domain.usecases.UserUseCase
import com.luisansal.jetpack.ui.features.manageusers.viewmodel.UserViewModel
import com.luisansal.jetpack.utils.mockPagedList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ManageUsersViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    lateinit var userViewModel: UserViewModel
    val userUseCae: UserUseCase = mockk()
    val observer: Observer<ListUserViewState> = mockk(relaxed = true)

    @Before
    fun berore() {
        userViewModel = UserViewModel(userUseCae)
        userViewModel.listUserViewState.observeForever(observer)
    }

    @Test
    fun `listar usuarios`() = runBlockingTest {
        val users = UsersMockDataHelper().getUsers()

        coEvery { userUseCae.getAllUser() } returns users

        userViewModel.getUsers()

        coVerify {
            observer.onChanged(ListUserViewState.LoadingState())
            observer.onChanged(ListUserViewState.SuccessState(users))
        }

    }

    @Test
    fun `error al obtener lista de usuarios`() = runBlockingTest {

        val exception = Exception("Custom error")

        coEvery { userUseCae.getAllUser() } throws exception

        userViewModel.getUsers()

        coVerify {
            observer.onChanged(ListUserViewState.ErrorState(exception))
        }

    }

    @Test
    fun `listar usuarios paginados`() = runBlockingTest {
        val users = UsersMockDataHelper().getUsers().mockPagedList()
        val liveDataUsers = MutableLiveData<PagedList<User>>()
        liveDataUsers.postValue(users)

        coEvery { userUseCae.getAllUserPaged() } returns liveDataUsers

        userViewModel.getUsersPaged()

        coVerify {
            observer.onChanged(ListUserViewState.LoadingState())
            observer.onChanged(ListUserViewState.SuccessPagedState(liveDataUsers))
        }

    }
}