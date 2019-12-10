package com.luisansal.jetpack.common.hardware

import android.content.Context
import android.net.ConnectivityManager
import com.luisansal.jetpack.BuildConfig

class AndroidHardwareInfoRetriever(private val context: Context): HardwareInfoRetriever {

    companion object {
        const val QAS_CODIGO = "QAS"
        const val PPR_CODIGO = "PPR"
        const val PRD_CODIGO = "PRD"
    }

    override fun get(): HardwareInfo {
        return HardwareInfo(buildVariant = getBuildVariant(),
                            currentNetworkStatus = getNetworkStatus())
    }

    private fun getNetworkStatus(): NetworkStatus {
        val connectivityManager: ConnectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        return if (networkInfo != null && networkInfo.isConnectedOrConnecting) {
            NetworkStatus.CONNECTED
        } else {
            NetworkStatus.DISCONNECTED
        }
    }

    private fun getBuildVariant(): BuildVariant {
        return when(BuildConfig.BUILD_TYPE.toUpperCase()) {
            QAS_CODIGO -> BuildVariant.QAS
            PPR_CODIGO -> BuildVariant.PPR
            PRD_CODIGO -> BuildVariant.PRD
            else -> throw Exception("Build type not supported")
        }
    }
}