package com.luisansal.jetpack.tests.base

import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Matchers

fun assertToolbarTitle(@IdRes toolbarId: Int, text: String) {
    Espresso.onView(
        Matchers.allOf(
            Matchers.instanceOf(TextView::class.java),
            ViewMatchers.withParent(withId(toolbarId))
        )
    ).check(ViewAssertions.matches(ViewMatchers.withText(text)))
}

fun assertDialogText(@StringRes stringId: Int) {
    Espresso.onView(ViewMatchers.withText(stringId))
        .inRoot(RootMatchers.isDialog()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
}

fun assertToastWithText(text: String, view: View) {
    Espresso.onView(ViewMatchers.withText(text))
        .inRoot(RootMatchers.withDecorView(Matchers.not(Matchers.`is`(view))))
        .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
}