package com.luisansal.jetpack.ui.features.multimedia

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.luisansal.jetpack.BuildConfig
import com.luisansal.jetpack.MainActivity
import com.luisansal.jetpack.R
import com.luisansal.jetpack.common.interfaces.TitleListener
import com.luisansal.jetpack.ui.utils.ImgDecodableModel
import com.luisansal.jetpack.ui.utils.injectFragment
import com.luisansal.jetpack.ui.utils.loadImageFromStorage
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.fragment_multimedia.*
import okhttp3.*
import okio.ByteString
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MultimediaFragment : Fragment(), TitleListener {

    private val multimediaViewModel: MultimediaViewModel by injectFragment()
    private var sampleImages = mutableListOf(R.drawable.image_1, R.drawable.image_2, R.drawable.image_3)
    private var imgDecodableModels = mutableListOf<ImgDecodableModel>()
    private lateinit var websocket: WebSocket

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
                    createNotificationChannel()
                    sendNotification("imagen guardada", "descripcion imagen guardada")
                    enviarNotificacionesAUsuarios()
                    pgMultimedia.visibility = View.INVISIBLE
                }
                is MultimediaViewState.SuccessFotoState -> {
                    imgDecodableModels.add(multimediaViewState.data)
                    updateCarouselView(sampleImages.size + imgDecodableModels.size)
                    pgMultimedia.visibility = View.INVISIBLE
                }
            }
        })

        instantiateWebSocket()
    }

    fun enviarNotificacionesAUsuarios(){
        val jsonObjectMessage = JSONObject()
        jsonObjectMessage.put("command", "groupchat")
        jsonObjectMessage.put("channel", CHANNEL_ID)
        jsonObjectMessage.put("message", "Imagen guardada")
        jsonObjectMessage.put("description", "Verifica la imagen que he guardado")

        websocket.send(jsonObjectMessage.toString())
    }

    fun instantiateWebSocket() {
        val client = OkHttpClient()
        val request = Request.Builder().url("ws://192.168.0.193:8090").build()
        websocket = client.newWebSocket(request, SocketListener(requireActivity(), this))
        val jsonObject = JSONObject()
        jsonObject.put("command", "subscribe")
        jsonObject.put("channel", CHANNEL_ID)
        websocket.send(jsonObject.toString())
    }

    internal class SocketListener(private val activity: FragmentActivity, private val multimediaFragment: MultimediaFragment) : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)

            activity.runOnUiThread {
                Toast.makeText(activity, "Conexión establecida", Toast.LENGTH_LONG).show()
            }
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            activity.runOnUiThread {
                val model = WebSocketModel(text)
                multimediaFragment.createNotificationChannel()
                multimediaFragment.sendNotification(model.message, model.description)
            }
        }
    }

    internal class WebSocketModel(json: String) : JSONObject(json) {
        val message: String = this.optString("message")
        val description: String = this.optString("description")
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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
        const val MULTIMEDIA_DIR = "androidjetpack/Multimedia"
        const val CHANNEL_ID = "GLOBAL_ANDROID"
        const val notificationId = 123
        fun newInstance(): MultimediaFragment {

            return MultimediaFragment();
        }
    }

    override val title: String = "Multimedia"

}