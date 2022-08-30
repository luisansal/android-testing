package com.luisansal.core.test.espresso

import android.view.View
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

fun onMatchAt(matcher: Matcher<View>, position: Int): Matcher<View> {
    return object : BaseMatcher<View>() {
        var counter = 0
        override fun matches(item: Any): Boolean {
            if (matcher.matches(item)) {
                if (counter == position) {
                    counter++
                    return true
                }
                counter++
            }
            return false
        }

        override fun describeTo(description: Description) {
            description.appendText("Element at hierarchy position $position")
        }
    }
}
