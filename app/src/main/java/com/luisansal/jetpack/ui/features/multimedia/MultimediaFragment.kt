package com.luisansal.jetpack.ui.features.multimedia

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.luisansal.jetpack.R
import com.luisansal.jetpack.common.interfaces.TitleListener
import com.luisansal.jetpack.ui.utils.ImgDecodableModel
import com.luisansal.jetpack.ui.utils.getImgDecodableModel
import com.luisansal.jetpack.ui.utils.loadImageFromStorage
import com.luisansal.jetpack.ui.utils.saveToInternalStorage
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.multimedia_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MultimediaFragment : Fragment(), TitleListener {

    var sampleImages = mutableListOf(R.drawable.image_1, R.drawable.image_2, R.drawable.image_3)
    var imgDecodableModels = mutableListOf<ImgDecodableModel>()
    var imgDecodableModelsLiveData = MutableLiveData<List<ImgDecodableModel>>()

    var imageListener = ImageListener { position, imageView ->
        if (position + 1 <= sampleImages.size)
            imageView.setImageResource(sampleImages[position])
        else
            imageView.loadImageFromStorage(imgDecodableModels.get(position-sampleImages.size).fileName)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.multimedia_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgDecodableModelsLiveData.observe(requireActivity(), Observer<List<ImgDecodableModel>> {
            updateCarouselView(sampleImages.size+it.size)
        })

        updateCarouselView(sampleImages.size, imageListener)
        onClickBtnAgregarImagen()
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

    fun saveImage(uri: Uri){
        val imgDecodableModel = uri.getImgDecodableModel(requireActivity())
        imgDecodableModels.add(imgDecodableModel)
        val bitMapImage = BitmapFactory.decodeFile(imgDecodableModel.imgDecodableString)

        bitMapImage.saveToInternalStorage(requireContext(), imgDecodableModel.fileName)
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
        }
    }


    companion object {

        const val GALLERY_REQUEST_CODE = 1
        fun newInstance(): MultimediaFragment {

            return MultimediaFragment();
        }
    }

    override val title: String = "Multimedia"
}