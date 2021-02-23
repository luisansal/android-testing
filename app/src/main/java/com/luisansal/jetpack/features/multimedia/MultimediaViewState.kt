package com.luisansal.jetpack.features.multimedia

import com.luisansal.jetpack.utils.FileModel

sealed class MultimediaViewState{
    data class LoadingState(val loading : Boolean) : MultimediaViewState()
    data class ErrorState(val e : Throwable) : MultimediaViewState()
    data class SuccessGalleryState(val data : List<FileModel?>) : MultimediaViewState()
    data class SuccessFotoState(val data : FileModel) : MultimediaViewState()
}