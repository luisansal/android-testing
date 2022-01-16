package com.luisansal.jetpack.features.manageusers

import android.os.Bundle
import com.luisansal.jetpack.core.base.BaseBindingActivity
import com.luisansal.jetpack.databinding.ActivityRoomBinding
import com.luisansal.jetpack.features.populate.PopulateViewModel
import com.luisansal.jetpack.features.populate.PopulateViewState
import org.koin.android.ext.android.inject

class RoomActivity : BaseBindingActivity() {
    private val binding by lazy {
        ActivityRoomBinding.inflate(layoutInflater).apply { lifecycleOwner = this@RoomActivity }
    }

    override fun getViewResource() = binding.root

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