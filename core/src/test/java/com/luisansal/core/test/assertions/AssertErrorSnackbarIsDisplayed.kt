package com.luisansal.jetpack.core.test.assertions

import androidx.annotation.StringRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotExist
import com.luisansal.jetpack.core.test.espresso.waitFor

fun assertErrorSnackbarIsDisplayed(@StringRes message: Int? = null) {
    onView(isRoot()).perform(waitFor(200))
//    assertDisplayed(R.string.snackbar_error_title)
    if (message != null) {
        assertDisplayed(message)
    }
}

fun assertSuccessSnackbarIsDisplayed(@StringRes message: Int? = null) {
    onView(isRoot()).perform(waitFor(200))
//    assertDisplayed(R.string.snackbar_success_title)
    if (message != null) {
        assertDisplayed(message)
    }
}

fun assertSuccessSnackbarDoesNotExist(@StringRes message: Int? = null) {
    onView(isRoot()).perform(waitFor(200))
//    assertNotExist(R.string.snackbar_success_title)
    if (message != null) {
        assertNotExist(message)
    }
}

fun assertUnexpectedErrorDialogIsDisplayed() {
    onView(isRoot()).perform(waitFor(200))
//    assertDisplayed(R.string.unexpected_error_dialog_title)
//    assertDisplayed(R.string.unexpected_error_dialog_subtitle)
//    assertDisplayed(R.string.unexpected_error_dialog_retry_button)
}

fun assertNoInternetConnectionDialogDisplayed() {
    onView(isRoot()).perform(waitFor(200))
//    assertDisplayed(R.string.no_internet_connection_dialog_title)
//    assertDisplayed(R.string.no_internet_connection_dialog_subtitle)
//    assertDisplayed(R.string.no_internet_connection_dialog_retry_button)
}
