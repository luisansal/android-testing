package pe.com.luisansal.core.test.assertions

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import org.hamcrest.CoreMatchers

fun assertLoadingViewIsDisplayed() {
    onView(withClassName(CoreMatchers.endsWith("BlockingLoadingView")))
        .check(matches(isDisplayed()))
}
