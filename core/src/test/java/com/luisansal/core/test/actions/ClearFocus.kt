package com.luisansal.core.test.actions

import android.R
import androidx.test.espresso.Espresso.closeSoftKeyboard

fun clearFocus() {
    closeSoftKeyboard()
    requestFocus(R.id.content)
}
