package com.luisansal.jetpack.core.components.edittext

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

@InverseBindingAdapter(attribute = "android:text")
fun getText(view: JetpackEditText): String? {
    return view.text
}

@BindingAdapter("android:textAttrChanged")
fun setListener(
    view: JetpackEditText,
    attrChange: InverseBindingListener
) {
    view.onTextChangeListener {
        attrChange.onChange()
    }
}