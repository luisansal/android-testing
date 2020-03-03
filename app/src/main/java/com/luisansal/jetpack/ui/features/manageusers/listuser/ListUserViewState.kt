package com.luisansal.jetpack.ui.features.manageusers.listuser

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.luisansal.jetpack.domain.entity.User

sealed class ListUserViewState  {

    data class ErrorState(val error: Throwable?) : ListUserViewState()
    data class LoadingState(val a: Int = 0) : ListUserViewState()
    data class SuccessState(val data: List<User>?) : ListUserViewState()
    data class SuccessPagedState(val data: LiveData<PagedList<User>>?) : ListUserViewState()
}