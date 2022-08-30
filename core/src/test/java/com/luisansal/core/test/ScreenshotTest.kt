package com.luisansal.core.test

import android.app.Activity
import android.app.Dialog
import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.fragment.app.Fragment
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import com.facebook.testing.screenshot.Screenshot.snap
import com.facebook.testing.screenshot.Screenshot.snapActivity
import com.luisansal.core.test.espresso.waitFor
import com.facebook.testing.screenshot.ViewHelpers

interface ScreenshotTest {

    fun compareScreenshot(activity: Activity) {
        Screenshot.snapActivity(activity).record()
    }

    fun compareScreenshot(dialog: Dialog) {
        Screenshot.snap(dialog.window!!.decorView).record()
    }

    fun compareScreenshot(view: View, heightInPx: Int) {
        ViewHelpers.setupView(view)
            .setExactHeightPx(heightInPx)
            .setExactWidthPx(displayMetrics.widthPixels)
            .layout()
        Screenshot.snap(view).record()
    }

    fun compareScreenshot(fragment: Fragment, heightInPx: Int) {
        val view = fragment.view!!
        val activity = fragment.activity!!
        waitForCIToCatchUp()
        activity.runOnUiThread {
            ViewHelpers.setupView(view)
                .setExactHeightPx(heightInPx)
                .setExactWidthPx(displayMetrics.widthPixels)
                .layout()
                .draw()
        }
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
        Screenshot.snap(view).record()
    }

    fun waitForCIToCatchUp() {
        onView(isRoot()).perform(waitFor(400))
    }

    private val displayMetrics: DisplayMetrics
        get() {
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val metrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(metrics)
            return metrics
        }

    fun Int.toPixels(): Int {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        return context.resources.getDimensionPixelSize(this)
    }
}
