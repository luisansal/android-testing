package com.luisansal.jetpack.features.maps.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisansal.jetpack.domain.usecases.MapsUseCase
import kotlinx.coroutines.launch
import com.luisansal.jetpack.data.Result

class MapsSearchViewModel(private val mapsUsecase: MapsUseCase) : ViewModel() {
    val viewState = MutableLiveData<MapsSearchViewState>()

    fun getPlaces(query: String) {
        viewState.value = MapsSearchViewState.LoadinState(true)
        viewModelScope.launch {

            when (val result = mapsUsecase.getPlaces(query)) {
                is Result.Success -> {
                    viewState.postValue(MapsSearchViewState.SuccessState(result.data ?: emptyList()))
                    viewState.value = MapsSearchViewState.LoadinState(false)
                }
                is Result.Error -> {
                    viewState.postValue(MapsSearchViewState.ErrorState(result.exception))
                    viewState.value = MapsSearchViewState.LoadinState(false)
                }
            }

        }
    }
}