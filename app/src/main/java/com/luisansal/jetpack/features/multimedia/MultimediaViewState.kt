package com.luisansal.jetpack.features.multimedia

import com.luisansal.jetpack.utils.ImgDecodableModel

sealed class MultimediaViewState{
    data class LoadingState(val x : Int = 0) : MultimediaViewState()
    data class ErrorState(val e : Throwable) : MultimediaViewState()
    data class SuccessGalleryState(val data : List<ImgDecodableModel>) : MultimediaViewState()
    data class SuccessFotoState(val data : ImgDecodableModel) : MultimediaViewState()
}