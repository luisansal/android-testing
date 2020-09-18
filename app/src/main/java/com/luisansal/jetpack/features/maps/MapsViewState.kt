package com.luisansal.jetpack.features.maps

import com.luisansal.jetpack.features.maps.model.MarkerUserVisitMapModel

sealed class MapsViewState  {
    data class ErrorState(val error: Throwable?) : MapsViewState()
    data class LoadingState(val a: Int = 0) : MapsViewState()
    data class SuccessVisistsState(val data: MarkerUserVisitMapModel?) : MapsViewState()
    data class SuccessUserSavedState(val data: Boolean) : MapsViewState()
}