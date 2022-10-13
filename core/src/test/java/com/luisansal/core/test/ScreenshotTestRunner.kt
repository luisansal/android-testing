package com.luisansal.jetpack.core.test

import android.os.Bundle
import androidx.test.runner.AndroidJUnitRunner
import com.facebook.testing.screenshot.ScreenshotRunner

var CurrentTestFilterMode: TestFilterMode = TestFilterMode.All

class AndroidTestRunner : AndroidJUnitRunner() {
    override fun onCreate(args: Bundle) {
        super.onCreate(args)
        ScreenshotRunner.onCreate(this, args)
    }

    override fun finish(resultCode: Int, results: Bundle) {
        ScreenshotRunner.onDestroy()
        super.finish(resultCode, results)
    }
}

class OnlyScreenshotTestRunner : AndroidJUnitRunner() {
    override fun onCreate(args: Bundle) {
        super.onCreate(args)
        CurrentTestFilterMode = TestFilterMode.OnlyScreenshot
        ScreenshotRunner.onCreate(this, args)
    }

    override fun finish(resultCode: Int, results: Bundle) {
        ScreenshotRunner.onDestroy()
        super.finish(resultCode, results)
    }
}

class OnlyNotScreenshotTestRunner : AndroidJUnitRunner() {
    override fun onCreate(args: Bundle) {
        super.onCreate(args)
        CurrentTestFilterMode = TestFilterMode.OnlyNotScreenshot
        ScreenshotRunner.onCreate(this, args)
    }

    override fun finish(resultCode: Int, results: Bundle) {
        ScreenshotRunner.onDestroy()
        super.finish(resultCode, results)
    }
}

sealed class TestFilterMode {
    object OnlyScreenshot : TestFilterMode()
    object OnlyNotScreenshot : TestFilterMode()
    object All : TestFilterMode()
}
