package com.luisansal.jetpack.core.test.actions

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

fun onAllChildViews(action: ViewAction) = OnAllChildViews(action)

class OnAllChildViews(private val action: ViewAction) : ViewAction {
    override fun getDescription(): String = "Performing action on all the view pager views"

    override fun getConstraints(): Matcher<View> {
        return allOf()
    }

    override fun perform(uiController: UiController?, view: View?) {
        if (view !is androidx.viewpager.widget.ViewPager) {
            return
        }

        (0..view.childCount)
            .map { view.getChildAt(it) }
            .forEach { action.perform(uiController, it) }
    }
}
