package com.luisansal.jetpack.features.chat

sealed class ChatViewState  {
    data class ErrorState(val error: Throwable?) : ChatViewState()
    data class LoadingState(val a: Int = 0) : ChatViewState()
    data class MessageSendedState(val info: String = "") : ChatViewState()
}