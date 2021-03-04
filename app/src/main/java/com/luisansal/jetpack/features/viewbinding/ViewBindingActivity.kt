package com.luisansal.jetpack.features.viewbinding

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
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
        viewModel.onStartCountDown()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.isBackClicked.observe(this, Observer {
            if (it) onBackPressed()
        })
    }

    fun onClickResendCode(view: View) {
        showMessage("onClickResendCode")
    }

    fun onClickValidateCode(view: View) {
        showMessage("onClickValidateCode")
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResumeCountDown()
    }

    override fun onStop() {
        super.onStop()
        viewModel.onStopCountDown()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroyCountDown()
    }
}