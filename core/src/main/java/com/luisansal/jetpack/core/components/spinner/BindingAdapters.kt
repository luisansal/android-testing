package com.luisansal.jetpack.core.components.spinner

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.luisansal.jetpack.core.utils.Validation

@BindingAdapter("app:list")
fun list(view: JetpackSpinner, list: List<JetpackSpinner.Model>?) {
    list?.let {
        view.dataSet = list
    }
}

@BindingAdapter("app:onSelected")
fun onSelected(view: JetpackSpinner, listener: (JetpackSpinner.Model) -> Unit) {
    view.onSelected = listener
}

@BindingAdapter("app:select")
fun select(view: JetpackSpinner, key: String?) {
    key?.let {
        view.select(key)
    }
}

@InverseBindingAdapter(attribute = "app:select")
fun select(view: JetpackSpinner): String? {
    return view.selected?.key
}

@BindingAdapter("app:selectAttrChanged")
fun setListener(
    view: JetpackSpinner,
    listener: InverseBindingListener
) {
    view.onItemSelectedListener {
        listener.onChange()
    }
}

@BindingAdapter("app:validate")
fun validate(view: JetpackSpinner, validation: Validation?) {
    validation?.let {
        val isValid = when (validation) {
            is Validation.ValidationRes -> {
                validation.validated
            }
            is Validation.ValidationString -> {
                validation.validated
            }
        }
        view.validate = isValid ?: false
    }
}