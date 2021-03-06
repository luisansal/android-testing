package com.luisansal.jetpack.components.spinner

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import androidx.constraintlayout.widget.ConstraintLayout
import com.luisansal.jetpack.R
import kotlinx.android.synthetic.main.jetpack_spinner.view.*

class JetpackSpinner(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
    companion object {
        const val EMPTY_KEY = "-1"
        const val SELECTED = "SELECTED"
        const val UNSELECTED = "UNSELECTED"
    }

    private val adapter by lazy {
        Adapter(context, R.layout.support_simple_spinner_dropdown_item)
    }

    init {
        inflate(context, R.layout.jetpack_spinner, this)

        spIzipay?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) = Unit
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (spIzipay?.tag != SELECTED) {
                    val model = adapter.getItem(p2) as Model
                    if (model.key != EMPTY_KEY) {
                        onItemSelectedListener?.invoke(model)
                        selected = model
                    }
                }
                spIzipay?.tag = UNSELECTED
            }
        }
        invalidateView()
    }

    var onItemSelectedListener: ((Model) -> Unit)? = null

    fun onItemSelectedListener(listener: ((Model) -> Unit)? = null) {
        onItemSelectedListener = listener
    }

    fun select(model: Model) {
        val index = setupAdapter.indexOf(model)
        spIzipay?.tag = SELECTED
        spIzipay?.setSelection(index)
        invalidateView()
    }

    var selected: Model? = null

    fun select(key: String) {
        var index = -1
        setupAdapter.forEachIndexed { indexLoop, model ->
            if (model.key == key) {
                index = indexLoop
                return@forEachIndexed
            }
        }
        if (index != -1) {
            spIzipay?.tag = SELECTED
            spIzipay?.setSelection(index)
        }
        invalidateView()
    }

    var setupAdapter: MutableList<Model> = mutableListOf()
        set(value) {
            val data = ArrayList(value)
            if (hint != "") {
                data.add(Model(EMPTY_KEY, hint))
            }
            adapter.dataSet = data
            spIzipay?.adapter = adapter

            if (value.isNotEmpty()) {
                spIzipay?.setSelection(data.size - 1)
            }
            field = data
            invalidateView()
        }

    var hint: String = ""
        set(value) {
            val data = ArrayList(setupAdapter)
            data.add(Model(EMPTY_KEY, value))
            setupAdapter = data
            field = value
            invalidateView()
        }

    private fun invalidateView() {
        invalidate()
        requestLayout()
    }

    data class Model(val key: String, val value: String)
}