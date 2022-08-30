package com.luisansal.core.test.actions

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.Matcher

fun removeItemAnimations(@IdRes id: Int) {
    onView(allOf(withId(id), isDisplayed()))
        .perform(removeViewItemAnimations())
}

fun removeViewItemAnimations(): ViewAction = RemoveItemAnimations()

private class RemoveItemAnimations : ViewAction {
    override fun getDescription(): String {
        return "Removing animations for recycler view"
    }

    override fun getConstraints(): Matcher<View> {
        return allOf(
            instanceOf(RecyclerView::class.java),
            withEffectiveVisibility(Visibility.VISIBLE))
    }

    override fun perform(uiController: UiController?, view: View?) {
        if (view is RecyclerView) {
            view.itemAnimator = null
        }
    }
}
