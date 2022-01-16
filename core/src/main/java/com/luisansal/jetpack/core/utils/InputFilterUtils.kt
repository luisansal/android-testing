package com.luisansal.jetpack.core.utils

import android.text.InputFilter
import android.text.Spanned
import android.text.method.PasswordTransformationMethod
import android.view.View
import java.util.regex.Pattern


class InputLettersFilter: InputFilter {
    override fun filter(
        cs: CharSequence,
        start: Int,
        end: Int,
        spanned: Spanned?,
        dStart: Int,
        dEnd: Int
    ): CharSequence? {
        if (cs == "") return cs
        if (cs == " " && spanned.toString().isBlank()) return ""
        return if (cs.toString().matches(Regex("[a-zA-Z ñÑ]+"))) {
            cs
        } else cs.dropLast(1)
    }
}


class InputEmojiFilter: InputFilter {
    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        for (index in start until end) {
            val type = Character.getType(source[index])
            if (type == Character.SURROGATE.toInt()) {
                return ""
            }
        }
        return null
    }
}

class MinMaxFilter(private val mIntMin: Int, private val mIntMax: Int) : InputFilter {
    override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence? {
        try {
            val input = (dest.toString() + source.toString()).toInt()
            if (isInRange(mIntMin, mIntMax, input)) return null
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
        return ""
    }

    private fun isInRange(a: Int, b: Int, c: Int): Boolean {
        return if (b > a) c in a..b else c in b..a
    }
}

class InputCellphoneFilter: InputFilter {

    override fun filter(
        source: CharSequence?, start: Int, end: Int, dest: Spanned?,
        dstart: Int, dend: Int
    ): CharSequence? {

        return if (source.toString().matches(Regex("[0-9]+"))) {
            source
        } else ""
    }


//    ^[0-9 ]{3}[ ]{1}[0-9 ]{3}[ ]{1}[0-9 ]{3}$

}


class InputAmountFilter: InputFilter {

    override fun filter(source: CharSequence?, start: Int,
                        end: Int, dest: Spanned?,
                        dstart: Int, dend: Int): CharSequence? {
        return if (!Pattern.compile("[0-9]{0,5}(\\.[0-9]{0,2})?")
                .matcher(dest.toString() + source.toString()).matches()) "" else null
    }

}

class NumericKeyBoardTransformationMethod :
    PasswordTransformationMethod() {
    override fun getTransformation(source: CharSequence, view: View?): CharSequence {
        return source
    }
}

//class InputCellphoneFilter: InputFilter {
//    override fun filter(source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int): CharSequence? {
//        val text = source
//        val text1 = dest.toString()
//        return source
//    }
//}