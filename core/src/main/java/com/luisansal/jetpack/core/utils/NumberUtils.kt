package com.luisansal.jetpack.core.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import kotlin.math.pow
import kotlin.math.roundToInt

fun Double.formaterNumberDecimal(decimals: Int): String {
    val patternDecimals = "0".repeat(decimals)
    val decimalFormatSymbols = DecimalFormatSymbols()
    val formatNumber: String
    decimalFormatSymbols.decimalSeparator = '.'
    val f = DecimalFormat("#,###.$patternDecimals", decimalFormatSymbols)
    formatNumber = f.format(this)
    var str = if (formatNumber == ".$patternDecimals") {
        "0.$patternDecimals"
    } else {
        formatNumber
    }
    return when (str.length) {
        3 -> "0$str"
        4 -> "$str"
        else -> str
    }
}

fun Double.toStringWithDecimals(decimals: Int): String {
    val patternDecimals = "0".repeat(decimals)
    val decimalFormatSymbols = DecimalFormatSymbols()
    val formatNumber: String
    decimalFormatSymbols.decimalSeparator = '.'
    val f = DecimalFormat(".$patternDecimals", decimalFormatSymbols)
    formatNumber = f.format(this)
    var str = if (formatNumber == ".$patternDecimals") {
        "0.$patternDecimals"
    } else {
        formatNumber
    }
    return when (str.length) {
        3 -> "0$str"
        4 -> "$str"
        else -> str
    }
}

fun Double.round(decimals: Int = 2): Double =
    ((this * 10.0.pow(decimals)).roundToInt()) * 1.0 / 10.0.pow(decimals)

fun Int.setZeroLeft(): String = if (this > 9)
    this.toString()
else {
    if (this in 1..9) {
        "0$this"
    } else
        "0"
}