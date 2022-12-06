package com.luisansal.jetpack.core.components.spinner

import android.content.Context
import android.util.AttributeSet
import com.luisansal.jetpack.core.utils.EMPTY_KEY

class JetpackSpinner(context: Context, attrs: AttributeSet?) : BaseJetpackSpinner<JetpackSpinner.Model>(context, attrs) {

    data class Model(val key: String, val value: String) {
        override fun toString(): String {
            return value
        }
    }

    var keyLoaded: String? = null

    override fun select(key: String) {
        if (key == EMPTY_KEY) {
            binding.spJetpack.setSelection(0)
            return
        }
        keyLoaded = key
        val index = dataSet.map { it.key }.indexOf(key)
        if (index != -1) {
            binding.spJetpack.setSelection(index)
        }
    }

    override var dataSet : List<Model> = emptyList()
        set(value) {
            field = value
            super.dataSet = value
            keyLoaded?.let { keyLoaded ->
                select(keyLoaded)
            }
        }

}