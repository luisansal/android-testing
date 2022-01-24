package com.luisansal.jetpack.features.manageusers

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.luisansal.jetpack.core.domain.entity.User

sealed class UserViewState {
    data class ErrorState(val error: Throwable?) : UserViewState()
    data class LoadingState(val isLoading: Boolean) : UserViewState()
    data class NewUserSuccess(val user: User?) : UserViewState()
    data class GetUserSuccessState(val user: User?) : UserViewState()
    data class DeleteSuccessState(val data: Boolean = false) : UserViewState()
    data class DeleteAllSuccessState(val data: Boolean = false) : UserViewState()
    data class ListSuccessState(val data: List<User>?) : UserViewState()
    data class ListSuccessPagedState(val data: LiveData<PagedList<User>>?) : UserViewState()
}