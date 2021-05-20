package com.luisansal.jetpack.features.sales.products

import android.os.Bundle
import com.luisansal.jetpack.base.BaseBindingActivity
import com.luisansal.jetpack.databinding.ActivityProductBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductActivity : BaseBindingActivity(){
    val binding by lazy {
        ActivityProductBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@ProductActivity
        }
    }
    override fun getViewResource() = binding.root
    val viewModel by viewModel<ProductViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.rvProductos.adapter = viewModel.adapter
    }

}