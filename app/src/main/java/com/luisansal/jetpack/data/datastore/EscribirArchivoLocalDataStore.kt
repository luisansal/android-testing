package com.luisansal.jetpack.data.datastore

import android.content.Context
import android.media.MediaScannerConnection
import android.os.Environment
import com.luisansal.jetpack.BuildConfig
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class EscribirArchivoLocalDataStore(val context: Context) {

    val LOG_NAME = "analytics_log_"
    val FOLDER_NAME = "/com.luisansal.jetpack/"
    val LOG_EXTENSION = ".txt"
    val LOG_FOO = "DEBUG AnalyticsLogger - "

    val dateFormat = SimpleDateFormat("dd-MM-yyyy")
    val dateFormatLog = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")

    fun guardarLog(tipoLog: String, log: String) {
        if (isDebug() && isExternalStorageWritable) {
            crearCarpetaSiNoExiste()
            val nombreArchivo = obtenerNombreDeArchivo()
            val archivoActual = leerArchivo(nombreArchivo)
            var nuevoLog = "${dateFormatLog.format(Date())} $LOG_FOO $tipoLog$log"
            nuevoLog = nuevoLog.takeIf { archivoActual.isEmpty() } ?: "$archivoActual \n$nuevoLog"
            val nuevoArchivo = File(nombreArchivo)
            val outputStream = FileOutputStream(nuevoArchivo)
            outputStream.write(nuevoLog.toByteArray())
            outputStream.close()
            MediaScannerConnection.scanFile(context, arrayOf(nuevoArchivo.absolutePath), null) { path, uri -> }
        }
    }

    private fun leerArchivo(nombreArchivo: String): String {
        return if (isDebug() && isExternalStorageReadable) {
            try {
                val br = BufferedReader(FileReader(nombreArchivo))

                var currentLine: String? = null
                val stringBuilder = StringBuilder()

                while ({ currentLine = br.readLine(); currentLine }() != null) {
                    stringBuilder.append(currentLine)
                    stringBuilder.append(System.getProperty("line.separator"))
                }
                return stringBuilder.toString()
            } catch (e: FileNotFoundException) {
                ""
            }
        } else ""
    }

    private fun crearCarpetaSiNoExiste() {
        val directory = File(Environment.getExternalStorageDirectory(), FOLDER_NAME)
        if (!directory.exists()) crearArchivoYObtener(directory)
    }

    private fun crearArchivoYObtener(directory: File): File {
        directory.mkdir()
        return directory
    }

    private fun obtenerNombreDeArchivo(): String {
        return "${Environment.getExternalStorageDirectory()}$FOLDER_NAME" +
                "$LOG_NAME${dateFormat.format(Date())}$LOG_EXTENSION"
    }

    private val isExternalStorageWritable: Boolean
        get() {
            val state = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == state
        }

    private val isExternalStorageReadable: Boolean
        get() {
            val state = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state
        }

    private fun isDebug() = BuildConfig.DEBUG
}