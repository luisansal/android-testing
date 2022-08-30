package com.luisansal.core.test.espresso

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isRoot

fun waitForViewPagerToScroll() {
    onView(isRoot()).perform(waitFor(200))
}
