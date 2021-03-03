package com.luisansal.jetpack.features.viewbinding

import android.view.View
import androidx.databinding.BindingAdapter
import com.luisansal.jetpack.utils.animateToUp

@BindingAdapter("keyBoarPOSShow")
fun setKeyBoarPOSShow(view: View, isShow: Boolean) {
    if (isShow)
        view.animateToUp(500f)
}