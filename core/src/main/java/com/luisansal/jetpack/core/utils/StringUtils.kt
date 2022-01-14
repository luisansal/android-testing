package com.luisansal.jetpack.core.utils

import android.text.Html
import android.text.Spanned
import android.util.Patterns
import androidx.annotation.ColorInt
import java.util.regex.Pattern

fun getFBPhotoUrlById(idFacebook: String): String {
    return "https://graph.facebook.com/$idFacebook/picture?type=large"
}

fun String.isPhoneNumberValid(): Boolean {
    val regex = "^((\\+\\d{2})(\\d{9})\$|^[9][0-9]{8}\$)"
    val compile = Pattern.compile(regex)
    return compile.matcher(this).matches()
}

fun String.isRucValid(): Boolean {
    val regex = "[0-9]{11}$"
    val compile = Pattern.compile(regex)
    return compile.matcher(this).matches()
}

fun String.isJustLettersValid(): Boolean {
    val regex = "[a-z áéíóúÁÉÍÓÚñÑA-Z]+"
    val compile = Pattern.compile(regex)
    return compile.matcher(this).matches()
}

fun String.isEmailValid(): Boolean {
    return !(this.isBlank() || Patterns.EMAIL_ADDRESS.matcher(this).matches()
        .not())
}

fun String.isCEValid(): Boolean {
    return (isNotEmpty() && length > 5 && length < 13)
}

val String.Companion.EMPTY: String
    get() = ""

val String.Companion.SPACE: String
    get() = " "

val String.Companion.LINEFEED: String
    get() = "\n"

val Char.Companion.EMPTY: Char
    get() = ' '

fun String.removeComma(): Double {
    return this.replace(",", "").toDouble()
}


fun String.hightlight(highLight: String, @ColorInt color: Int): Spanned {
    val сolorString = String.format("%X", color).substring(2)

    val description = this
    val indexOfQuery = description.indexOf(highLight)
    val queryHtml = "<font color='#$сolorString'>$highLight</font>"

    val restStrStart = description.substring(0, indexOfQuery)
    val queryLength = highLight.length
    val restStrEnd = description.substring(restStrStart.length + queryLength)
    return Html.fromHtml("${restStrStart}$queryHtml$restStrEnd")
}