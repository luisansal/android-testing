package com.luisansal.jetpack.core.components.spinner

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.luisansal.jetpack.core.R
import com.luisansal.jetpack.core.databinding.JetpackSpinnerBinding

abstract class BaseJetpackSpinner<T>(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    val adapter by lazy {
        Adapter<T>(
            context = context,
            items = mutableListOf()
        )
    }

    val binding by lazy {
        JetpackSpinnerBinding.inflate(LayoutInflater.from(context), this, true)
    }

    init {
        if (isInEditMode) {
            inflate(context, R.layout.jetpack_spinner, this)
        } else {
            binding.spJetpack.adapter = adapter
            binding.spJetpack.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) = Unit
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val model = adapter.getItem(p2) as T
                    selected = model
                    onItemSelectedListener?.invoke(model)
                    onSelected?.invoke(model)
                }
            }
            invalidateView()
        }
    }

    var onSelected: ((T) -> Unit)? = null

    private var onItemSelectedListener: ((T) -> Unit)? = null

    fun onItemSelectedListener(listener: ((T) -> Unit)) {
        onItemSelectedListener = listener
    }

    fun select(model: T) {
        val index = dataSet.indexOf(model)
        binding.spJetpack.setSelection(index)
    }

    abstract fun select(key: String)

    var selected: T? = null

    open var dataSet: List<T> = emptyList()
        set(value) {
            field = value
            adapter.clear()
            adapter.addAll(value)
            adapter.notifyDataSetChanged()
            if (value.isNotEmpty()) {
                this.select(value.first())
            }
        }

    private fun invalidateView() {
        invalidate()
        requestLayout()
    }

    var validate: Boolean = false
        set(value) {
            if (!value) {
                binding.spJetpack.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red_normal))
            } else {
                binding.spJetpack.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.transparent))
            }
            field = value
        }
}