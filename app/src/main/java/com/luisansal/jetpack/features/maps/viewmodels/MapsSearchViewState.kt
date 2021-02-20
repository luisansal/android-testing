package com.luisansal.jetpack.features.maps.viewmodels

import com.luisansal.jetpack.domain.entity.Place
import java.lang.Exception

sealed class MapsSearchViewState {
    data class SuccessState(val data: List<Place>) : MapsSearchViewState()
    data class LoadinState(val loading: Boolean) : MapsSearchViewState()
    data class ErrorState(val e:Exception) : MapsSearchViewState()
}