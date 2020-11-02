package com.luisansal.jetpack.features.maps

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisansal.jetpack.data.Result
import com.luisansal.jetpack.domain.entity.Visit
import com.luisansal.jetpack.domain.usecases.MapsUseCase
import com.luisansal.jetpack.domain.usecases.UserUseCase
import com.luisansal.jetpack.domain.usecases.VisitUseCase
import com.luisansal.jetpack.features.maps.model.MarkerUserVisitMapModel
import kotlinx.coroutines.launch
import java.lang.Exception

class MapsViewModel(private val userUseCase: UserUseCase, private val visitUseCase: VisitUseCase, private val mapsUseCase: MapsUseCase) : ViewModel() {

    val mapViewState = MutableLiveData<MapsViewState>()

    fun getVisits(dni: String) {
        mapViewState.postValue(MapsViewState.LoadingState())

        viewModelScope.launch {
            try {
                val user = userUseCase.getUser(dni)
                val mapsViewModel = visitUseCase.getByUser(dni)?.let { user?.let { it1 -> MarkerUserVisitMapModel(it, it1) } }
                mapViewState.postValue(MapsViewState.SuccessVisistsState(mapsViewModel))
            } catch (exception: Exception) {
                mapViewState.postValue(MapsViewState.ErrorState(exception))
            }

        }
    }

    fun saveOneVisitForUser(visit: Visit, userId: Long) {

        mapViewState.postValue(MapsViewState.LoadingState())

        viewModelScope.launch {
            try {
                mapViewState.postValue(MapsViewState.SuccessUserSavedState(visitUseCase.saveOneVisitForUser(visit, userId)))
            } catch (exception: Exception) {
                mapViewState.postValue(MapsViewState.ErrorState(exception))
            }
        }
    }

    fun sendPosition(message: String, latiude: Double, longitude: Double) {
        mapViewState.postValue(MapsViewState.LoadingState())

        viewModelScope.launch {
            when (val result = mapsUseCase.sendPosition(message, latiude, longitude)) {
                is Result.Success -> {
                    mapViewState.postValue(MapsViewState.SuccessSendPosition(result.data ?: false))
                }
            }
        }
    }
}

