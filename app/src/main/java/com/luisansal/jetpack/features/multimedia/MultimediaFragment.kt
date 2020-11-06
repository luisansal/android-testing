package com.luisansal.jetpack.features.multimedia

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import com.luisansal.jetpack.BuildConfig
import com.luisansal.jetpack.features.main.MainActivity
import com.luisansal.jetpack.R
import com.luisansal.jetpack.base.BaseFragment
import com.luisansal.jetpack.common.interfaces.TitleListener
import com.luisansal.jetpack.utils.FileModel
import com.luisansal.jetpack.utils.injectFragment
import com.luisansal.jetpack.utils.loadImageFromStorage
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.fragment_multimedia.*
import okhttp3.*
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MultimediaFragment : BaseFragment(), TitleListener {

    private val multimediaViewModel: MultimediaViewModel by injectFragment()
    private var sampleImages = mutableListOf(R.drawable.image_1, R.drawable.image_2, R.drawable.image_3)
    private var fileModels = mutableListOf<FileModel?>()
    private val websocket by lazy {
        val client = OkHttpClient()
        val request = Request.Builder().url("ws://192.168.8.131:8092").build()
        client.newWebSocket(request, webSocketListener)
    }

    fun setupWebSocket(){
        val jsonObject = JSONObject()
        jsonObject.put("command", "subscribe")
        jsonObject.put("channel", CHANNEL_ID)
        websocket.send(jsonObject.toString())
    }

    private val webSocketListener = object:  WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)

            requireActivity().runOnUiThread {
                Toast.makeText(activity, "Multimedia Conexión establecida", Toast.LENGTH_LONG).show()
            }
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            Log.d("socket failure",t.message)
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)

            requireActivity().runOnUiThread {
                val model = WebSocketModel(text)
                createNotificationChannel()
                sendNotification(model.message, model.description)
            }
        }
    }

    internal class WebSocketModel(json: String) : JSONObject(json) {
        val message: String = this.optString("message")
        val description: String = this.optString("description")
    }

    var imageListener = ImageListener { position, imageView ->
        if (position + 1 <= sampleImages.size)
            imageView.setImageResource(sampleImages[position])
        else
            imageView.loadImageFromStorage(
                    _directoryName = MULTIMEDIA_DIR,
                    _fileName = fileModels.get(position - sampleImages.size)?.file?.name
            )
    }

    override fun getViewIdResource() = R.layout.fragment_multimedia

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateCarouselView(sampleImages.size, imageListener)
        onClickBtnAgregarImagen()
        onClickBtnTomarFoto()
        onclickBtnCompartirEnlace()
        onclickBtnCompartirImagen()

        multimediaViewModel.multimediaViewState.observe(viewLifecycleOwner, Observer { multimediaViewState ->
            when (multimediaViewState) {
                is MultimediaViewState.LoadingState -> {
                    pgMultimedia.visibility = View.VISIBLE
                }
                is MultimediaViewState.SuccessGalleryState -> {
                    fileModels.addAll(multimediaViewState.data)
                    updateCarouselView(sampleImages.size + fileModels.size)
                    createNotificationChannel()
                    sendNotification("imagen guardada", "descripcion imagen guardada")
                    enviarNotificacionesAUsuarios()
                    pgMultimedia.visibility = View.INVISIBLE
                }
                is MultimediaViewState.SuccessFotoState -> {
                    fileModels.add(multimediaViewState.data)
                    updateCarouselView(sampleImages.size + fileModels.size)
                    pgMultimedia.visibility = View.INVISIBLE
                }
            }
        })

        setupWebSocket()
    }

    fun enviarNotificacionesAUsuarios() {
        val jsonObjectMessage = JSONObject()
        jsonObjectMessage.put("command", "groupchat")
        jsonObjectMessage.put("channel", CHANNEL_ID)
        jsonObjectMessage.put("message", "Imagen guardada")
        jsonObjectMessage.put("description", "Verifica la imagen que he guardado")

        websocket.send(jsonObjectMessage.toString())
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = getString(R.string.channel_name)
        val descriptionText = getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun sendNotification(title: String, description: String) {

        // Create an explicit intent for an Activity in your app
        val intent = Intent(requireContext(), MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(MainActivity.POSITION, 2)
        }
        val pendingIntent = PendingIntent.getActivity(requireActivity(), 0, intent, 0)

        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.image_1)
                .setContentTitle(title)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)

        with(NotificationManagerCompat.from(requireContext())) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())
        }

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

    fun onclickBtnCompartirEnlace() {
        btnCompartirEnlace.setOnClickListener {
            compartirEnlace()
        }
    }

    fun onclickBtnCompartirImagen() {
        btnCompartirImagen.setOnClickListener {
            compartirImagen()
        }
    }

    fun compartirEnlace() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "http://www.gmail.com")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    fun compartirImagen() {
        mPhotoUri?.let { uri ->
            val file = File(uri.path)
            val newUri = FileProvider.getUriForFile(requireContext(), BuildConfig.APPLICATION_ID + ".provider", file)
            val shareIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, newUri)
                type = "image/*"
            }
            startActivity(Intent.createChooser(shareIntent, resources.getText(R.string.send_to)))
        }
    }

    var mPhotoUri: Uri? = null

    @Throws(IOException::class)
    private fun createImageFile(): File { // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        //This is the directory in which the file will be created. This is the default location of Camera photos
        val storageDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), CAMERA_DIR)
        if (!storageDir.exists()) storageDir.mkdir()
//        val storageDir = File(requireContext().getExternalFilesDir(Environment.DIRECTORY_DCIM), MULTIMEDIA_DIR)
//        if (!storageDir.exists()) storageDir.mkdir()

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
        const val MULTIMEDIA_DIR = "Androidjetpack/Multimedia"
        const val CAMERA_DIR = "Camera"
        const val CHANNEL_ID = "MULTIMEDIA_GLOBAL_ANDROID"
        const val notificationId = 123
        fun newInstance(): MultimediaFragment {

            return MultimediaFragment();
        }
    }

    override val title: String = "Multimedia"

}