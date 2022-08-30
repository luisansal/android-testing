package com.luisansal.core.test.intent

import android.content.Intent
import android.net.Uri
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.equalTo

fun intendedTermsAndConditionsOpened() {
    intendedOpenUrl("https://n1-headache.com/terms-and-conditions/?theme=dark")
}

fun intendedOpenUrl(url: String) {
    intended(allOf(
        hasAction(equalTo(Intent.ACTION_VIEW)),
        hasData(Uri.parse(url))))
}
