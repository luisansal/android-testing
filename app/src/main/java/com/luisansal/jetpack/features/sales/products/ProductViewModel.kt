package com.luisansal.jetpack.features.sales.products

import androidx.lifecycle.ViewModel

class ProductViewModel : ViewModel() {
    val adapter by lazy {
        ProductAdapter()
    }

    init {
        val models = listOf(
            ProductAdapter.Model("producto 1", "descripcion 1", "S/20.00"),
            ProductAdapter.Model("producto 2", "descripcion 2", "S/15.00"),
            ProductAdapter.Model("producto 3", "descripcion 3", "S/18.00"),
            ProductAdapter.Model("producto 4", "descripcion 4", "S/25.00")
        )
        adapter.dataSet = models
    }
}