package com.luisansal.jetpack.features.viewbinding

import android.os.Bundle
import com.luisansal.jetpack.base.BaseBindingActivity
import com.luisansal.jetpack.databinding.ActivityViewBindingBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ViewBindingActivity : BaseBindingActivity() {

    private val binding by lazy {
        ActivityViewBindingBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@ViewBindingActivity
        }
    }
    private val viewModel by viewModel<ViewBindingViewModel>()
    override fun getViewResource() = binding.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
    }
}