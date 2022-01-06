package pe.com.luisansal.core.test.actions

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

fun clickNotFullyVisibleView(text: String) {
    onView(withText(text)).check(matches(allOf(isEnabled(), isClickable()))).perform(
        object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isEnabled()
            }

            override fun getDescription(): String {
                return "click plus button"
            }

            override fun perform(uiController: UiController, view: View) {
                view.performClick()
            }
        }
    )
}
