package com.luisansal.jetpack.core.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import pe.com.luisansal.core.R

class JetpackAutocompleteTextView : AppCompatAutoCompleteTextView {
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        addTextChangedListener {
            if (it.toString().isNotEmpty())
                setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_close_green, 0)
            else
                setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }

        val x: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_close_green)
        x?.setBounds(0, 0, x.intrinsicWidth, x.intrinsicHeight)
        setOnTouchListener { _, motionEvent ->
            if (compoundDrawables[2] == null) {
                false
            }
            if (motionEvent.action != MotionEvent.ACTION_UP) {
                false
            }
            if (motionEvent.x > width - paddingRight - (x?.intrinsicWidth ?: 0)) {
                setText("")
                setCompoundDrawables(null, null, null, null)
            }
            false
        }
    }

}