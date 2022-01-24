package com.luisansal.jetpack.features.manageusers.listuser

import androidx.lifecycle.MutableLiveData
import com.luisansal.jetpack.core.base.BaseViewModel
import com.luisansal.jetpack.core.utils.EMPTY

class ListUserViewModel : BaseViewModel() {
    val name = MutableLiveData(EMPTY)
    val adapterUsuarios: PagedUserAdapter by lazy {
        PagedUserAdapter()
    }
}