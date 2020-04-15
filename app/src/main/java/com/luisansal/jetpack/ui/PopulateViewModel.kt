package com.luisansal.jetpack.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisansal.jetpack.domain.usecases.PopulateUseCase
import kotlinx.coroutines.launch
import java.lang.Exception

class PopulateViewModel(private val populateUseCase: PopulateUseCase) : ViewModel() {

    val populateViewState = MutableLiveData<PopulateViewState>()

    fun start() {
        populateViewState.postValue(PopulateViewState.LoadingState())

        viewModelScope.launch {

            try {
                populateViewState.postValue(PopulateViewState.SuccessState(populateUseCase.start()))
            } catch (e: Exception) {
                populateViewState.postValue(PopulateViewState.ErrorState(e))
            }

        }

    }

}