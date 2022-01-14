package com.luisansal.jetpack.features.auth

import android.content.Intent
import android.os.Bundle
import com.luisansal.jetpack.R
import com.luisansal.jetpack.core.base.BaseBindingActivity
import com.luisansal.jetpack.core.data.preferences.AuthSharedPreferences
import com.luisansal.jetpack.databinding.ActivityLoginBinding
import com.luisansal.jetpack.features.main.MainActivity
import org.koin.android.ext.android.inject
import java.util.*

class LoginActivity : BaseBindingActivity() {

    private val mBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@LoginActivity
        }
    }

    override fun getViewResource() = mBinding.root

    private val authSharedPreferences by inject<AuthSharedPreferences>()

    override fun onStart() {
        super.onStart()
        if (authSharedPreferences.isLogged) {
            if (authSharedPreferences.tokenExpires <= Calendar.getInstance().timeInMillis) {
                showMessage(R.string.session_expired)
                return
            }
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeObservers()


    }

    private fun subscribeObservers() {

    }

}