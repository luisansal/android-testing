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
import com.luisansal.jetpack.core.utils.FileModel
import com.luisansal.jetpack.core.utils.getFileName
import com.luisansal.jetpack.core.utils.rotateImageIfRequired
import com.luisansal.jetpack.core.utils.saveToInternalStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException


class MultimediaViewModel(private val context: Context) : ViewModel() {

    var multimediaViewState = MutableLiveData<MultimediaViewState>()

    private fun saveImage(uri: Uri, isCamera: Boolean = false): FileModel? {
        var bitMapImage: Bitmap? = null
        if (isCamera) {
            bitMapImage = BitmapFactory.decodeFile(uri.path).rotateImageIfRequired(uri)
        } else {
            try {
                context.contentResolver.openFileDescriptor(uri, "r").use { pfd ->
                    if (pfd != null) {
                        bitMapImage = BitmapFactory.decodeFileDescriptor(pfd.fileDescriptor)
                    }
                }
            } catch (ex: IOException) {
            }
        }

        return bitMapImage?.saveToInternalStorage(
                context = context,
                _directoryName = MultimediaFragment.MULTIMEDIA_DIR,
                _fileName = uri.getFileName(context)!!
        )
    }

    fun takeImageFromGallery(data: Intent?) {
        multimediaViewState.postValue(MultimediaViewState.LoadingState(true))
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val fileModels = mutableListOf<FileModel?>()
                if (data?.clipData != null) {
                    val mClipData = data.clipData

                    for (i in 0 until mClipData!!.itemCount) {
                        val item = mClipData.getItemAt(i)
                        val uri = item.uri

                        fileModels.add(saveImage(uri = uri))
                    }
                    Log.v("LOG_TAG", "Selected Images" + fileModels.size)
                } else {
                    //data.getData return the content URI for the selected Image
                    val selectedImage: Uri = data?.data!!
                    fileModels.add(saveImage(uri = selectedImage))
                }
                multimediaViewState.postValue(MultimediaViewState.SuccessGalleryState(fileModels))
            }
            multimediaViewState.postValue(MultimediaViewState.LoadingState(false))
        }

    }

    fun takeImageFromCameraFoto(mPhotoUri: Uri) {
        multimediaViewState.postValue(MultimediaViewState.LoadingState(true))

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                multimediaViewState.postValue(saveImage(uri = mPhotoUri, isCamera = true)?.let { MultimediaViewState.SuccessFotoState(it) })
            }
            multimediaViewState.postValue(MultimediaViewState.LoadingState(false))
        }

    }
}