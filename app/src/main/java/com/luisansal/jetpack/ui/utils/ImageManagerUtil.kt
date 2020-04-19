package com.luisansal.jetpack.ui.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import androidx.annotation.NonNull
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class ImageManagerUtil(private val context: Context?) {
     var directoryName = "images"
     var fileName = "image.png"
     var external = false

    fun save(bitmapImage: Bitmap) : String? {
        var fileOutputStream: FileOutputStream? = null
        try {
            val fileModel = createFile();
            fileOutputStream = FileOutputStream(fileModel?.file)
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            return fileModel?.strAbsoultePath
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

    internal class FileModel(val file : File?,val strAbsoultePath : String?)

    @NonNull
    private fun createFile(): FileModel? {
        val directory: File = if (external) {
            getAlbumStorageDir(directoryName)
        } else {
            context?.getDir(directoryName, Context.MODE_PRIVATE)!!
        }
        if (!directory.exists() && !directory.mkdirs()) {
            Log.e("ImageSaver", "Error creating directory $directory")
        }
        return FileModel(File(directory, fileName),directory.absolutePath)
    }

    private fun getAlbumStorageDir(albumName: String): File {
        return File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName)
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