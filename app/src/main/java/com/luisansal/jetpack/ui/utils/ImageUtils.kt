package com.luisansal.jetpack.ui.utils

import android.app.Activity
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView

fun Bitmap.saveToInternalStorage(context: Context, _fileName: String): String? {
    return ImageManagerUtil(context).apply {
        directoryName = "androidJetpack"
        external = true
        fileName = _fileName
    }.save(this)
}


fun ImageView.loadImageFromStorage(_fileName: String) {
    val bitmap = ImageManagerUtil(this.context).apply {
        directoryName = "androidJetpack"
        external = true
        fileName = _fileName
    }.load()

    this.setImageBitmap(bitmap)
}

private fun String.getFileName(): String {
    var fileName = this
    val cut: Int = this.lastIndexOf('/')
    if (cut != -1) {
        fileName = this.substring(cut + 1)
    }
    return fileName
}

data class ImgDecodableModel (val imgDecodableString : String, val fileName : String)

/**
 * First return imgDecodableString
 * Second return fileNameString
 */
fun Uri.getImgDecodableModel(activity : Activity) : ImgDecodableModel{
    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
    // Get the cursor
    val cursor: Cursor = activity.contentResolver.query(this, filePathColumn, null, null, null)!!
    // Move to first row
    cursor.moveToFirst()
    //Get the column index of MediaStore.Images.Media.DATA
    val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
    //Gets the String value in the column
    val imgDecodableString: String = cursor.getString(columnIndex)
    cursor.close()
    return ImgDecodableModel(imgDecodableString, imgDecodableString.getFileName())
}