package com.luisansal.jetpack.features.manageusers

import android.os.Bundle
import com.luisansal.jetpack.R
import com.luisansal.jetpack.core.base.BaseActivity
import com.luisansal.jetpack.features.populate.PopulateViewModel
import com.luisansal.jetpack.features.populate.PopulateViewState
import org.koin.android.ext.android.inject

class RoomActivity : BaseActivity() {
    override fun getViewIdResource() = R.layout.activity_room
    private val popoulateViewModel: PopulateViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        popoulateViewModel.populateViewState.observe(::getLifecycle, ::observerPopulateData)
        popoulateViewModel.start()
    }

    private fun observerPopulateData(populateViewState: PopulateViewState) {
        when (populateViewState) {
            is PopulateViewState.LoadingState -> {
                showLoading(true)
            }
            is PopulateViewState.SuccessState -> {
                showLoading(false)
            }
        }
    }

}