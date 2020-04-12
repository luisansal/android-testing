package com.luisansal.jetpack.ui.features.maps

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisansal.jetpack.domain.entity.Visit
import com.luisansal.jetpack.domain.usecases.UserUseCase
import com.luisansal.jetpack.domain.usecases.VisitUseCase
import com.luisansal.jetpack.ui.features.maps.model.MarkerUserVisitMapModel
import com.luisansal.jetpack.ui.viewstate.BaseViewState
import kotlinx.coroutines.launch
import java.lang.Exception

class MapsViewModel(private val userUseCase: UserUseCase,private val visitUseCase: VisitUseCase) : ViewModel() {

    val mapViewState = MutableLiveData<BaseViewState>()
    val saveVisitUserViewState = MutableLiveData<BaseViewState>()

    fun getVisits(dni: String) {
        mapViewState.postValue(BaseViewState.LoadingState())

        viewModelScope.launch {
            try {
                val user = userUseCase.getUser(dni);
                val mapsViewModel = visitUseCase.getByUser(dni)?.let { user?.let { it1 -> MarkerUserVisitMapModel(it, it1) } }
                mapViewState.postValue(BaseViewState.SuccessState(mapsViewModel))
            } catch (exception: Exception) {
                mapViewState.postValue(BaseViewState.ErrorState(exception))
            }

        }
    }

    fun saveOneVisitForUser(visit: Visit, userId : Long){

        viewModelScope.launch {
            saveVisitUserViewState.postValue(BaseViewState.LoadingState())
            try{
                saveVisitUserViewState.postValue(BaseViewState.SuccessState(visitUseCase.saveOneVisitForUser(visit,userId)))
            } catch (exception : Exception){
                saveVisitUserViewState.postValue(BaseViewState.ErrorState(exception))
            }

        }

    }
}

