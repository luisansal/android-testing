package com.luisansal.jetpack.tests.base

import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers

fun assertCustomView(@IdRes toolbarId: Int, text: String) {
    onView(
        Matchers.allOf(
            Matchers.instanceOf(TextView::class.java),
            ViewMatchers.withParent(withId(toolbarId))
        )
    ).check(matches(withText(text)))
}