package com.luisansal.jetpack.core.test.actions

import android.R
import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId

fun writeToEditTextWithKeyboard(@IdRes id: Int, text: String) {
    onView(withId(id)).perform(typeText(text))
    requestFocus(R.id.content)
}
