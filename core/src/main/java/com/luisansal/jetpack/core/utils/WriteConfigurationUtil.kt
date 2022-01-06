package com.luisansal.jetpack.core.utils

import android.content.Context


object WriteConfigurationUtil {
    fun writeConfiguration(ctx: Context) {
        try {
            ctx.openFileOutput("config.txt", Context.MODE_PRIVATE).use({ openFileOutput ->

                openFileOutput.write("This is a test1.".toByteArray())
                openFileOutput.write("This is a test2.".toByteArray())
            })
        } catch (e: Exception) {
            // not handled
        }

    }
}