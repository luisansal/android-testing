package pe.com.luisansal.core.test.actions

import android.view.View
import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

fun requestFocus(@IdRes id: Int) {
    onView(withId(id)).perform(requestFocus())
}

private fun requestFocus(): ViewAction = RequestFocusAction()

private class RequestFocusAction : ViewAction {
    override fun getDescription(): String {
        return "Focus requested"
    }

    override fun getConstraints(): Matcher<View> {
        return allOf(withEffectiveVisibility(VISIBLE))
    }

    override fun perform(uiController: UiController?, view: View?) {
        view?.isFocusable = true
        view?.isFocusableInTouchMode = true
        view?.requestFocus()
    }
}
