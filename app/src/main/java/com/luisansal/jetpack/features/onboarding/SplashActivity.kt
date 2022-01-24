package com.luisansal.jetpack.features.onboarding

import android.os.Bundle
import com.luisansal.jetpack.R
import com.luisansal.jetpack.core.base.BaseBindingActivity
import com.luisansal.jetpack.databinding.ActivitySplashBinding

class SplashActivity : BaseBindingActivity(){
    private val binding by lazy {
        ActivitySplashBinding.inflate(layoutInflater).apply { lifecycleOwner = this@SplashActivity }
    }
    override fun getViewResource() = binding.root

    override fun getViewIdResource() = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}