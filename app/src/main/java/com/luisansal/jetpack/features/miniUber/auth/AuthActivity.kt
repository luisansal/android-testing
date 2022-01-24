package com.luisansal.jetpack.features.miniUber.auth

import android.view.View
import androidx.navigation.Navigation
import com.luisansal.jetpack.R
import com.luisansal.jetpack.core.base.BaseBindingActivity
import com.luisansal.jetpack.databinding.ActivityMiniuberAuthBinding

class AuthActivity : BaseBindingActivity() {
    private val binding by lazy {
        ActivityMiniuberAuthBinding.inflate(layoutInflater).apply { lifecycleOwner = this@AuthActivity }
    }

    private val navController by lazy {
        Navigation.findNavController(this, R.id.auth_nav_host_fragment)
    }

    override fun getViewResource() = binding.root
}