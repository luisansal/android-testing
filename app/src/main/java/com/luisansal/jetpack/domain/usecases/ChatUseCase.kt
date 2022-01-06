package com.luisansal.jetpack.domain.usecases

import com.luisansal.jetpack.core.data.Result
import com.luisansal.jetpack.data.datastore.ChatCloudStore
import com.luisansal.jetpack.core.data.network.response.StatusResponse

class ChatUseCase(private val chatCloudStore: ChatCloudStore) {

    suspend fun sendMessage(mesage: String): Result<StatusResponse>? {
        val result = chatCloudStore.sendMessage(mesage)
        return result
    }
}
