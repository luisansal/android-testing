package com.luisansal.jetpack.features.design

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.luisansal.jetpack.R
import com.luisansal.jetpack.base.BaseFragment

class DesignFragment : BaseFragment() {

    private lateinit var viewModel: DesignViewModel
    override fun getViewIdResource() = R.layout.fragment_design

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DesignViewModel::class.java)
    }

}
