package com.luisansal.jetpack.ui.features.multimedia

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.luisansal.jetpack.BuildConfig
import com.luisansal.jetpack.R
import com.luisansal.jetpack.common.interfaces.TitleListener
import com.luisansal.jetpack.ui.utils.*
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.fragment_multimedia.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MultimediaFragment : Fragment(), TitleListener {

    private val multimediaViewModel: MultimediaViewModel by injectFragment()
    var sampleImages = mutableListOf(R.drawable.image_1, R.drawable.image_2, R.drawable.image_3)
    var imgDecodableModels = mutableListOf<ImgDecodableModel>()

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

        updateCarouselView(sampleImages.size, imageListener)
        onClickBtnAgregarImagen()
        onClickBtnTomarFoto()

        multimediaViewModel.multimediaViewState.observe(viewLifecycleOwner, Observer { multimediaViewState ->
            when (multimediaViewState) {
                is MultimediaViewState.LoadingState -> {
                    pgMultimedia.visibility = View.VISIBLE
                }
                is MultimediaViewState.SuccessGalleryState -> {
                    imgDecodableModels.addAll(multimediaViewState.data)
                    updateCarouselView(sampleImages.size + imgDecodableModels.size)
                    pgMultimedia.visibility = View.INVISIBLE
                }
                is MultimediaViewState.SuccessFotoState -> {
                    imgDecodableModels.add(multimediaViewState.data)
                    updateCarouselView(sampleImages.size + imgDecodableModels.size)
                    pgMultimedia.visibility = View.INVISIBLE
                }
            }
        })
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

    var mPhotoUri: Uri? = null

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) when (requestCode) {
            GALLERY_REQUEST_CODE -> {
                multimediaViewModel.takeImageFromGallery(data)
            }
            CAMERA_REQUEST_CODE -> {
                mPhotoUri?.let { multimediaViewModel.takeImageFromCameraFoto(it) }
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