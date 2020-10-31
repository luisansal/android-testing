package com.luisansal.jetpack.domain.exceptions

import com.luisansal.jetpack.domain.entity.User
import java.lang.Exception

class UserExistException(val user: User) : Exception()