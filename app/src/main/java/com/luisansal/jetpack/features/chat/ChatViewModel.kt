package com.luisansal.jetpack.features.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisansal.jetpack.core.data.Result
import com.luisansal.jetpack.domain.usecases.ChatUseCase
import kotlinx.coroutines.launch

class ChatViewModel(private val chatUseCase: ChatUseCase) : ViewModel() {
    val viewState = MutableLiveData<ChatViewState>()

    fun sendMessage(message: String) {
        viewState.postValue(ChatViewState.LoadingState())
        viewModelScope.launch {
            when (val result = chatUseCase.sendMessage(message)) {
                is Result.Success -> {
                    viewState.postValue(ChatViewState.MessageSendedState(result.data?.message ?: ""))
                }
                is Result.Error -> {
                    viewState.postValue(ChatViewState.ErrorState(result.exception))
                }
            }
        }
    }
}