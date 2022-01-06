package pe.com.luisansal.core.test.actions

import android.view.View
import android.widget.ScrollView
import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.Matcher

fun hideScrollbars(@IdRes id: Int) {
    onView(withId(id)).perform(hideScrollbars())
}

private fun hideScrollbars(): ViewAction = HideScrollbars()

private class HideScrollbars : ViewAction {
    override fun getDescription(): String {
        return "Hide scrollbars"
    }

    override fun getConstraints(): Matcher<View> {
        return allOf(
            instanceOf(ScrollView::class.java),
            withEffectiveVisibility(VISIBLE))
    }

    override fun perform(uiController: UiController?, view: View?) {
        if (view !is ScrollView) {
            return
        }

        view.isVerticalScrollBarEnabled = false
        view.isHorizontalScrollBarEnabled = false
    }
}
