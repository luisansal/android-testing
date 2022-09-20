package com.luisansal.jetpack.tests.base

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.MotionEvents
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import com.luisansal.jetpack.features.auth.LoginActivity
import kotlinx.coroutines.*
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.junit.Ignore
import org.junit.Rule
import org.junit.runner.RunWith
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn as bClickOn
import com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo as bWriteTo

@Ignore("BaseIntegrationTest")
@RunWith(AndroidJUnit4::class)
abstract class BaseIntegrationTest {
    companion object {
        const val AUX_DELAY_TIME = 2200L
    }

    fun waitForScreen(delayTime: Long = AUX_DELAY_TIME, callback: (() -> Unit)? = null) {
        runBlocking {
            delay(delayTime)
            withContext(Dispatchers.Default) {
                callback?.let { it() }
            }
        }
    }

    fun intended(matcher: Matcher<Intent>, callback: (() -> Unit)? = null) {
        Intents.intended(matcher)
        callback?.let { it() }
    }

    fun tryIntented(matcher: Matcher<Intent>, callback: (() -> Matcher<Intent>)? = null) {
        try {
            Intents.intended(matcher)
        } catch (e: Throwable) {
            callback?.let {
                Intents.intended(it())
            }
        }
    }

    fun assertViewIsDisplayed(@IdRes idRes: Int) {
        onView(withId(idRes))
            .check(matches(isDisplayed()))
    }

    fun assertViewIsDisplayed(viewsId: List<Int>) {
        viewsId.forEach {
            assertViewIsDisplayed(it)
        }
    }

    fun assertSnackbarWithText(@StringRes message: Int) {
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(message)))
    }

    fun assertToolbarTitle(@IdRes toolbarId: Int, text: String) {
        onView(
            allOf(
                Matchers.instanceOf(TextView::class.java),
                withParent(withId(toolbarId))
            )
        ).check(matches(withText(text)))
    }

    fun assertTextView(@IdRes id: Int, text: String) {
        onView(withId(id)).check(matches(withText(text)))
    }

    fun assertTextView(@IdRes id: Int, @StringRes text: Int) {
        onView(withId(id)).check(matches(withText(text)))
    }

    fun assertDialogText(@StringRes stringId: Int) {
        onView(withText(stringId))
            .inRoot(RootMatchers.isDialog())
            .check(matches(isDisplayed()))
    }

    fun assertToastWithText(text: String, view: View) {
        onView(withText(text))
            .inRoot(RootMatchers.withDecorView(Matchers.not(Matchers.`is`(view))))
            .check(matches(isDisplayed()))
    }

    fun touchDownAndUp(@IntegerRes res: Int, x: Int, y: Int) {
        onView(withId(res)).perform(touchDownAndUp(x, y));
    }

    fun touchScreen(x: Int = 300, y: Int = 300){
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).click(x, y)
    }

    private fun touchDownAndUp(x: Int, y: Int): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isDisplayed()
            }

            override fun getDescription(): String {
                return "Send touch events."
            }

            override fun perform(uiController: UiController, view: View) {
                // Get view absolute position
                val location = IntArray(2)
                view.getLocationOnScreen(location)

                // Offset coordinates by view position
                val coordinates = floatArrayOf(x.toFloat() + location[0], y.toFloat() + location[1])
                val precision = floatArrayOf(1f, 1f)

                // Send down event, pause, and send up
                val down = MotionEvents.sendDown(uiController, coordinates, precision).down
                uiController.loopMainThreadForAtLeast(200)
                MotionEvents.sendUp(uiController, down, coordinates)
            }
        }
    }
}
