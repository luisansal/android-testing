package com.luisansal.jetpack.features.multimedia

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisansal.jetpack.utils.ImgDecodableModel
import com.luisansal.jetpack.utils.getImgDecodableModel
import com.luisansal.jetpack.utils.rotateImageIfRequired
import com.luisansal.jetpack.utils.saveToInternalStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MultimediaViewModel(private val context: Context) : ViewModel() {

    var multimediaViewState = MutableLiveData<MultimediaViewState>()

    fun saveImage(uri: Uri, isCamera: Boolean = false): ImgDecodableModel {
        val imgDecodableModel: ImgDecodableModel
        val bitMapImage: Bitmap
        if (isCamera) {
            imgDecodableModel = uri.getImgDecodableModel()
            bitMapImage = BitmapFactory.decodeFile(imgDecodableModel.imgDecodableString).rotateImageIfRequired(uri)
        } else {
            imgDecodableModel = uri.getImgDecodableModel(context)
            bitMapImage = BitmapFactory.decodeFile(imgDecodableModel.imgDecodableString)
        }

        bitMapImage.saveToInternalStorage(
                context = context,
                _directoryName = MultimediaFragment.MULTIMEDIA_DIR,
                _fileName = imgDecodableModel.fileName!!
        )
        return imgDecodableModel
    }

    fun takeImageFromGallery(data: Intent?) {
        multimediaViewState.postValue(MultimediaViewState.LoadingState())

        viewModelScope.launch(Dispatchers.IO) {

            val imgDecodableModels = mutableListOf<ImgDecodableModel>()
            if (data?.clipData != null) {
                val mClipData = data.clipData

                for (i in 0 until mClipData!!.itemCount) {
                    val item = mClipData.getItemAt(i)
                    val uri = item.uri

                    imgDecodableModels.add(saveImage(uri = uri))
                }
                Log.v("LOG_TAG", "Selected Images" + imgDecodableModels.size)
            } else {
                //data.getData return the content URI for the selected Image
                val selectedImage: Uri = data?.data!!
                imgDecodableModels.add(saveImage(uri = selectedImage))
            }
            multimediaViewState.postValue(MultimediaViewState.SuccessGalleryState(imgDecodableModels))
        }
    }

    fun takeImageFromCameraFoto(mPhotoUri: Uri) {
        multimediaViewState.postValue(MultimediaViewState.LoadingState())

        viewModelScope.launch(Dispatchers.IO) {
            multimediaViewState.postValue(MultimediaViewState.SuccessFotoState(saveImage(uri = mPhotoUri, isCamera = true)))
        }

    }
}