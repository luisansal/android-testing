package com.luisansal.jetpack.features.viewbinding

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import com.luisansal.jetpack.R
import com.luisansal.jetpack.core.utils.animateToUp

@BindingAdapter("keyBoarPOSShow")
fun setKeyBoarPOSShow(view: View, isShow: Boolean) {
    if (isShow)
        view.animateToUp(500f)
}

@BindingAdapter("waitingTime")
fun setWaitingTime(view: TextView, time: Long) {
    val context = view.context
    val fullTimeSeconds = (time / 1000).toInt()
    val minutes = (fullTimeSeconds / 60)
    val seconds = fullTimeSeconds % 60
    val strFinal = String.format("%d:%02d", minutes, seconds)
    val strSufix = if (minutes == 0) " segundos" else " minutos"
    val text = String.format(context.getString(R.string.waiting_time_seconds), strFinal, strSufix)
    view.text = text
}

@BindingAdapter("app:validateButton")
fun validateButton(view: MaterialButton, isValidate: Boolean) {
    if(isValidate){
        view.setBackgroundColor(view.context.getColor(R.color.green_light))
    } else {
        view.setBackgroundColor(view.context.getColor(R.color.red_main))
    }
}