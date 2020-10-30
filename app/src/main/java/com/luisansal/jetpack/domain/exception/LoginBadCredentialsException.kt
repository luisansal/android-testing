package com.luisansal.jetpack.domain.exception

import java.lang.Exception

class LoginBadCredentialsException(val email: String) : Exception()