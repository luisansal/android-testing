package com.luisansal.jetpack.ui.features.multimedia

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.luisansal.jetpack.BuildConfig
import com.luisansal.jetpack.R
import com.luisansal.jetpack.common.interfaces.TitleListener
import com.luisansal.jetpack.ui.utils.*
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.fragment_multimedia.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class MultimediaFragment : Fragment(), TitleListener {

    var sampleImages = mutableListOf(R.drawable.image_1, R.drawable.image_2, R.drawable.image_3)
    var imgDecodableModels = mutableListOf<ImgDecodableModel>()
    var imgDecodableModelsLiveData = MutableLiveData<List<ImgDecodableModel>>()

    var imageListener = ImageListener { position, imageView ->
        if (position + 1 <= sampleImages.size)
            imageView.setImageResource(sampleImages[position])
        else
            imageView.loadImageFromStorage(
                    _directoryName = MULTIMEDIA_DIR,
                    _fileName = imgDecodableModels.get(position - sampleImages.size).fileName
            )
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_multimedia, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgDecodableModelsLiveData.observe(requireActivity(), Observer<List<ImgDecodableModel>> {
            updateCarouselView(sampleImages.size + it.size)
        })

        updateCarouselView(sampleImages.size, imageListener)
        onClickBtnAgregarImagen()
        onClickBtnTomarFoto()
    }

    fun updateCarouselView(pageCount: Int, listener: ImageListener? = null) {
        listener?.let {
            carouselView?.setImageListener(it)
        }
        carouselView?.pageCount = pageCount
    }

    fun onClickBtnAgregarImagen() {
        btnAgregarImagen.setOnClickListener {
            pickFromGallery()
        }
    }

    fun onClickBtnTomarFoto() {
        btnTomarFoto.setOnClickListener {
            captureFromCamera()
        }
    }

    var mPhotoUri : Uri? = null

    @Throws(IOException::class)
    private fun createImageFile(): File { // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        //This is the directory in which the file will be created. This is the default location of Camera photos
        val storageDir = File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera")
        val image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",  /* suffix */
                storageDir /* directory */
        )
        mPhotoUri = Uri.fromFile(image)
        return image
    }

    private fun captureFromCamera() {
        try {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(requireContext(), BuildConfig.APPLICATION_ID + ".provider", createImageFile()))
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }

    private fun pickFromGallery() { //Create an Intent with action as ACTION_PICK
        val intent = Intent(Intent.ACTION_PICK)
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.type = "image/*"
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        // Launching the Intent
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    fun saveImage(uri: Uri, isCamera: Boolean = false, _bitmap: Bitmap? = null) {
        val imgDecodableModel: ImgDecodableModel
        val bitMapImage : Bitmap
        if (isCamera) {
            imgDecodableModel = uri.getImgDecodableModel()
            bitMapImage = BitmapFactory.decodeFile(imgDecodableModel.imgDecodableString).rotateImageIfRequired(uri)
        } else {
            imgDecodableModel = uri.getImgDecodableModel(requireActivity())
            bitMapImage = BitmapFactory.decodeFile(imgDecodableModel.imgDecodableString)
        }

        imgDecodableModels.add(imgDecodableModel)

        bitMapImage.saveToInternalStorage(
                context = requireContext(),
                _directoryName = MULTIMEDIA_DIR,
                _fileName = imgDecodableModel.fileName
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) when (requestCode) {
            GALLERY_REQUEST_CODE -> {
                pgMultimedia.visibility = View.VISIBLE
                CoroutineScope(Dispatchers.IO).launch {
                    if (data?.clipData != null) {
                        val mClipData = data.clipData!!

                        for (i in 0 until mClipData.itemCount) {
                            val item = mClipData.getItemAt(i)
                            val uri = item.uri
                            saveImage(uri)
                        }
                        Log.v("LOG_TAG", "Selected Images" + imgDecodableModels.size)
                    } else {
                        //data.getData return the content URI for the selected Image
                        val selectedImage: Uri = data?.data!!
                        saveImage(selectedImage)
                    }
                    pgMultimedia.visibility = View.INVISIBLE
                    imgDecodableModelsLiveData.postValue(imgDecodableModels)
                }
            }
            CAMERA_REQUEST_CODE -> {
                pgMultimedia.visibility = View.VISIBLE
                CoroutineScope(Dispatchers.IO).launch {

                    saveImage(mPhotoUri!!, isCamera = true)

                    pgMultimedia.visibility = View.INVISIBLE
                    imgDecodableModelsLiveData.postValue(imgDecodableModels)
                }
            }
        }
    }


    companion object {

        const val GALLERY_REQUEST_CODE = 1
        const val CAMERA_REQUEST_CODE = 2
        const val MULTIMEDIA_DIR: String = "androidjetpack/Multimedia"

        fun newInstance(): MultimediaFragment {

            return MultimediaFragment();
        }
    }

    override val title: String = "Multimedia"

}