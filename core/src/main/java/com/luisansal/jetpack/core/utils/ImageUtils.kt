package com.luisansal.jetpack.core.utils

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import android.net.Uri
import android.os.Environment
import android.provider.OpenableColumns
import android.util.Log
import android.widget.ImageView
import androidx.annotation.NonNull
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


class ImageManager(private val context: Context?, val compressFormat: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG) {
    val compressFormatSrt = when (compressFormat) {
        Bitmap.CompressFormat.PNG -> "png"
        Bitmap.CompressFormat.JPEG -> "jpg"
        else -> "png"
    }

    var directoryName = "images"
    var fileName: String = when (compressFormat) {
        Bitmap.CompressFormat.PNG -> "image.png"
        Bitmap.CompressFormat.JPEG -> "image.jpg"
        else -> "image.png"
    }
        set(value) {
            field = "$value.${compressFormatSrt}"
        }

    var external = true

    fun save(bitmapImage: Bitmap): FileModel? {
        var fileOutputStream: FileOutputStream? = null
        try {
            val fileModel = createFile()
            fileOutputStream = FileOutputStream(fileModel?.file)
            bitmapImage.compress(compressFormat, 100, fileOutputStream)
            return fileModel
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fileOutputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }

    @NonNull
    fun createFile(): FileModel? {
        val directory: File = if (external) {
            getAlbumStorageDir(directoryName)
        } else {
            context?.getDir(directoryName, Context.MODE_PRIVATE)!!
        }
        if (!directory.exists() && !directory.mkdirs()) {
            Log.e("ImageSaver", "Error creating directory $directory")
        }
        return FileModel(File(directory, fileName), directory.absolutePath)
    }

    private fun getAlbumStorageDir(albumName: String): File {
        return File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName)
        //return File(context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES), albumName)
    }

    fun isExternalStorageWritable(): Boolean {
        val state: String = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED.equals(state)
    }

    fun isExternalStorageReadable(): Boolean {
        val state: String = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)
    }

    fun load(): Bitmap? {
        var inputStream: FileInputStream? = null
        try {
            inputStream = FileInputStream(createFile()?.file)
            return BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }
}

data class FileModel(val file: File?, val strAbsoultePath: String?)

fun Bitmap.saveToInternalStorage(
    context: Context, _directoryName: String, _fileName: String,
    _format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG, _external: Boolean? = true
): FileModel? {

    return ImageManager(context, _format).apply {
        directoryName = _directoryName
        external = _external!!
        fileName = _fileName
    }.save(this)
}

fun ImageView.loadImageFromStorage(_directoryName: String, _fileName: String?, _external: Boolean? = true) {
    val bitmap = ImageManager(this.context).apply {
        directoryName = _directoryName
        external = _external!!
        if (_fileName != null) {
            fileName = _fileName
        }
    }.load()

    this.setImageBitmap(bitmap)
}

fun Uri.getFileName(context: Context): String? {
    var result: String? = null
    if (this.scheme == "content") {
        val cursor: Cursor = context.contentResolver.query(this, null, null, null, null)!!
        try {
            if (cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        } finally {
            cursor.close()
        }
    }
    if (result == null) {
        result = this.path
        val cut = result?.lastIndexOf('/')!!
        if (cut != -1) {
            result = result.substring(cut + 1)
        }
    }
    return result
}

fun Bitmap.rotateBitmap(angle: Float): Bitmap {
    val matrix = Matrix().apply {
        postRotate(angle)
    }
    val rotatedImg = Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
    this.recycle()
    return rotatedImg
}

@Throws(IOException::class)
fun Bitmap.rotateImageIfRequired(selectedImage: Uri): Bitmap {
    val ei = ExifInterface(selectedImage.path ?: "")
    val orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
    return when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> this.rotateBitmap(90F)
        ExifInterface.ORIENTATION_ROTATE_180 -> this.rotateBitmap(180F)
        ExifInterface.ORIENTATION_ROTATE_270 -> this.rotateBitmap(270F)
        else -> this
    }
}

fun Drawable?.bitmapDescriptorFromVector(): BitmapDescriptor {
    val vectorDrawable = this
    vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
    val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    vectorDrawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}