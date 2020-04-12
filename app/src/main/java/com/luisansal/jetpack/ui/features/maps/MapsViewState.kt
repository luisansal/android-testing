package com.luisansal.jetpack.ui.features.maps

import com.luisansal.jetpack.domain.entity.Visit

sealed class MapsViewState  {
    data class ErrorState(val error: Throwable?) : MapsViewState()
    data class LoadinState(val a: Int = 0) : MapsViewState()
    data class SuccessVisistsState(val vists: List<Visit>) : MapsViewState()
}