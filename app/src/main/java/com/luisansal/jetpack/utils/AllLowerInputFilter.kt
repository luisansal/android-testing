package com.luisansal.jetpack.utils

import android.text.InputFilter
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils

class AllLowerInputFilter : InputFilter {

    override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int): CharSequence? {

        for (i in start until end) {
            if (source[i].isUpperCase()) {
                val v = CharArray(end - start)
                TextUtils.getChars(source, start, end, v, 0)
                val s = String(v).toLowerCase()

                return if (source is Spanned) {
                    val sp = SpannableString(s)
                    TextUtils.copySpansFrom(source, start, end, null, sp, 0)
                    sp
                } else {
                    s
                }
            }
        }

        return null // keep original
    }
}