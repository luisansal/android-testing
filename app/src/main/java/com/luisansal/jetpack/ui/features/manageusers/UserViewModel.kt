package com.luisansal.jetpack.ui.features.manageusers

import androidx.lifecycle.MutableLiveData
import com.luisansal.jetpack.domain.entity.User

class UserViewModel {

    var users = MutableLiveData<List<User>>()

}
