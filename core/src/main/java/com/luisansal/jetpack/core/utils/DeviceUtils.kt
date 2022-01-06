package com.luisansal.jetpack.core.utils

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.telephony.TelephonyManager
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*


const val DEVICE = "Android"
fun tieneNfc(context: Context): Boolean =
    context.packageManager?.hasSystemFeature(PackageManager.FEATURE_NFC) == true

fun getPlartformDevice(): String = DEVICE.plus(String.SPACE).plus(Build.VERSION.RELEASE)

fun getDeviceModel(): String = Build.MANUFACTURER.plus(String.SPACE).plus(Build.MODEL)

fun isAndroidEightorMore() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

fun getVersionApp(context: Context): String {
    return try {
        val pInfo: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        pInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        ""
    }
}

fun getOperativeSystem(): String {
    return DEVICE
}

fun getSdkVersion(): String {
    return "SDK Version ${Build.VERSION.SDK_INT}"
}

fun getLang(): String {
    return Locale.getDefault().language
}

fun getDeviceRam(context: Context): String {
    val activityManager = context.getSystemService(Activity.ACTIVITY_SERVICE) as ActivityManager;
    val memoryInfo = ActivityManager.MemoryInfo();
    activityManager.getMemoryInfo(memoryInfo);

    val totalRam = memoryInfo.totalMem / (1024 * 1024);
    return "$totalRam MB"
}

fun getDeviceAndroidVersion(): String {
    return "Device Android Version ${Build.VERSION.RELEASE}"
}

fun getDeviceKernelVersion(): String {
    return try {
        val p = Runtime.getRuntime().exec("uname -a")
        var `is`: InputStream? = null
        `is` = if (p.waitFor() == 0) {
            p.inputStream
        } else {
            p.errorStream
        }
        val br = BufferedReader(
            InputStreamReader(`is`),
            DEFAULT_BUFFER_SIZE
        )
        val line: String = br.readLine()
        br.close()
        line
    } catch (ex: Exception) {
        "ERROR: " + ex.message
    }
}

fun getDeviceBuildNumber(): String {
    return Build.FINGERPRINT
}

fun getOperatorName(context: Context): String {
    val manager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    return manager.networkOperatorName
}

enum class NetworkType(val value: String) {
    Wifi("WIFI"),
    NotConnected("-"),
    G2("2G"),
    G3("3G"),
    G4("4G"),
    G5("5G")
}

fun getNetworkClass(context: Context): String {

    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val info = cm.activeNetworkInfo
    if (info == null || !info.isConnected) return NetworkType.NotConnected.value // not connected
    if (info.type == ConnectivityManager.TYPE_WIFI) return NetworkType.Wifi.value
    if (info.type == ConnectivityManager.TYPE_MOBILE) {
        val networkType = info.subtype
        return when (networkType) {
            TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN, TelephonyManager.NETWORK_TYPE_GSM -> NetworkType.G2.value
            TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_HSPAP, TelephonyManager.NETWORK_TYPE_TD_SCDMA -> NetworkType.G3.value
            TelephonyManager.NETWORK_TYPE_LTE, TelephonyManager.NETWORK_TYPE_IWLAN, 19 -> NetworkType.G4.value
            TelephonyManager.NETWORK_TYPE_NR -> NetworkType.G5.value
            else -> "?"
        }
    }
    return "?"
}