package com.luisansal.jetpack.core.utils

import android.content.Context
import android.util.TypedValue

inline fun Float.toDp(context: Context): Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP, this, context.resources.displayMetrics
)

inline fun Int.toDp(context: Context): Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
)

inline fun Int.toSp(context: Context): Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_SP, this.toFloat(), context.resources.displayMetrics
)