package com.luisansal.jetpack.ui.features.manageusers.newuser

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.luisansal.jetpack.domain.entity.User

sealed class UserViewState  {
    data class ErrorState(val error: Throwable?) : UserViewState()
    data class LoadingState(val a: Int = 0) : UserViewState()
    data class CrearGetSuccessState(val user: User?) : UserViewState()
    data class DeleteSuccessState(val data: Boolean = false) : UserViewState()
    data class ListSuccessState(val data: List<User>?) : UserViewState()
    data class ListSuccessPagedState(val data: LiveData<PagedList<User>>?) : UserViewState()
}