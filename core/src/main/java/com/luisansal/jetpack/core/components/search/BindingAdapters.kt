package com.luisansal.jetpack.core.components.search

import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

@BindingAdapter("android:hint")
fun setHint(view: JetpackSearchView, @StringRes hint: Int) {
    view.hint = view.context.getString(hint)
}

@InverseBindingAdapter(attribute = "android:text")
fun getText(view: JetpackSearchView): String? {
    return view.text
}

@BindingAdapter("android:textAttrChanged")
fun setListener(
    view: JetpackSearchView,
    attrChange: InverseBindingListener
) {
    view.onTextChangeListener {
        attrChange.onChange()
    }
}