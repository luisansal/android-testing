package com.luisansal.jetpack.features.maps

import com.luisansal.jetpack.core.base.BaseBindingActivity
import com.luisansal.jetpack.databinding.ActivityMainMapsBinding

class MainMapsActivity : BaseBindingActivity() {
    private val binding by lazy {
        ActivityMainMapsBinding.inflate(layoutInflater).apply { lifecycleOwner = this@MainMapsActivity }
    }

    override fun getViewResource() = binding.root
}