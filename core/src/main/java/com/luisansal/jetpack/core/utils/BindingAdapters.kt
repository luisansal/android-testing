package com.luisansal.jetpack.core.utils

import android.text.InputFilter
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

@BindingAdapter("toLowerCase")
fun toLowerCase(view: EditText, isLowerCase: Boolean?) {
    isLowerCase?.let {
        if(it){
            view.filters = arrayOf<InputFilter>(AllLowerInputFilter())
        } else {
            view.filters = null
        }
    }
}

@BindingAdapter("app:loadImage")
fun loadImage(view: ImageView?, @DrawableRes imageId: Int) {
    view?.setImageResource(imageId)
}

@BindingAdapter("requestFocus")
fun requestFocus(view: EditText, requestFocus: Boolean?) {
    requestFocus?.also {
        if (it) {
            view.requestFocus()
            view.showKeyboard()
        }
    }
}