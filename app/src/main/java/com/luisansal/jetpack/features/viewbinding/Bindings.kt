package com.luisansal.jetpack.features.viewbinding

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.luisansal.jetpack.R
import com.luisansal.jetpack.utils.animateToUp

@BindingAdapter("keyBoarPOSShow")
fun setKeyBoarPOSShow(view: View, isShow: Boolean) {
    if (isShow)
        view.animateToUp(500f)
}

@BindingAdapter("waitingTime")
fun setWaitingTime(view: TextView, time: Long) {
    val context = view.context
    val timeStr = (time / 1000).toInt().toString()
    val text = String.format(context.getString(R.string.waiting_time_seconds), timeStr)
    view.text = text
}