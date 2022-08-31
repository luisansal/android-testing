package com.luisansal.jetpack.tests.base

import android.content.Intent
import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.util.HumanReadables
import androidx.test.espresso.util.TreeIterables
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.*
import org.hamcrest.Matcher
import org.junit.Ignore
import org.junit.runner.RunWith
import java.util.concurrent.TimeoutException

@Ignore("BaseIntegrationTest")
@RunWith(AndroidJUnit4::class)
abstract class BaseIntegrationTest {
    companion object {
        const val VIEW_TIME_OUT = 5000
        const val AUX_DELAY_TIME = 1000L
    }

    fun waitForView(viewId: Int, callback: () -> Unit) {
        runBlocking {
            delay(AUX_DELAY_TIME)
            Espresso.onView(ViewMatchers.isRoot()).perform(object : ViewAction {
                override fun getConstraints(): Matcher<View> {
                    return ViewMatchers.isRoot()
                }

                override fun getDescription(): String {
                    return "wait for a specific view with id $viewId; during $VIEW_TIME_OUT millis."
                }

                override fun perform(uiController: UiController, rootView: View) {
                    uiController.loopMainThreadUntilIdle()
                    val startTime = System.currentTimeMillis()
                    val endTime = startTime + VIEW_TIME_OUT
                    val viewMatcher = ViewMatchers.withId(viewId)

                    do {
                        // Iterate through all views on the screen and see if the view we are looking for is there already
                        for (child in TreeIterables.breadthFirstViewTraversal(rootView)) {
                            // found view with required ID
                            if (viewMatcher.matches(child)) {
                                return
                            }
                        }
                        // Loops the main thread for a specified period of time.
                        // Control may not return immediately, instead it'll return after the provided delay has passed and the queue is in an idle state again.
                        uiController.loopMainThreadForAtLeast(100)
                    } while (System.currentTimeMillis() < endTime) // in case of a timeout we throw an exception -> test fails
                    throw PerformException.Builder()
                        .withCause(TimeoutException())
                        .withActionDescription(this.description)
                        .withViewDescription(HumanReadables.describe(rootView))
                        .build()
                }
            })
            callback()
        }
    }

    fun intended(matcher: Matcher<Intent>, callback: () -> Unit) {
        runBlocking {
            delay(AUX_DELAY_TIME)
            Intents.intended(matcher)
            callback()
        }
    }
}
