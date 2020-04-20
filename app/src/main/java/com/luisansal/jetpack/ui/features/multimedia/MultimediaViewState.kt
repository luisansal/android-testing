package com.luisansal.jetpack.ui.features.multimedia

import com.luisansal.jetpack.ui.utils.ImgDecodableModel

sealed class MultimediaViewState{
    data class LoadingState(val x : Int = 0) : MultimediaViewState()
    data class ErrorState(val e : Throwable) : MultimediaViewState()
    data class SuccessGalleryState(val data : List<ImgDecodableModel>) : MultimediaViewState()
    data class SuccessFotoState(val data : ImgDecodableModel) : MultimediaViewState()
}