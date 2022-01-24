package com.luisansal.jetpack.core.components.search

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.luisansal.jetpack.core.components.ConstraintSavedInstanceLayout
import com.luisansal.jetpack.core.utils.EMPTY
import pe.com.luisansal.core.R
import pe.com.luisansal.core.databinding.JetpackSearchviewBinding

class JetpackSearchView(context: Context, attrs: AttributeSet) : ConstraintSavedInstanceLayout(context, attrs) {
    var text: String? = null
        set(value) {
            field = value
            binding.text = value
        }
        get() {
            return binding.text
        }

    var hint: String? = null
        set(value) {
            field = value
            binding.hint = value
        }

    var onTextChangeListener: ((String) -> Unit)? = null
    fun onTextChangeListener(listener: ((String) -> Unit)?) {
        onTextChangeListener = listener
    }

    val binding by lazy {
        JetpackSearchviewBinding.inflate(LayoutInflater.from(context), this, true)
    }

    init {
        if (isInEditMode)
            inflate(context, R.layout.jetpack_searchview, this)
        else {
            binding.etSearch.addTextChangedListener {
                onTextChangeListener?.invoke(it.toString())
                if (it.toString().isEmpty()) {
                    binding.btnCancel.visibility = View.GONE
                    binding.lySearch.setBackgroundResource(R.drawable.ic_shape_rectangle_lightgray_border_green)
                } else {
                    binding.btnCancel.visibility = View.VISIBLE
                    binding.etSearch.setTextColor(ContextCompat.getColor(context, R.color.green_dark))
                    binding.lySearch.setBackgroundResource(R.drawable.ic_shape_rectangle_lightgreen_border_green)
                }
            }
            binding.etSearch.requestFocus()
            binding.onClickCancel = OnClickListener {
                binding.etSearch.setText(EMPTY)
                binding.btnCancel.visibility = View.GONE
            }
        }
    }
}