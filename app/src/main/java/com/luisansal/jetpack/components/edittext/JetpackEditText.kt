package com.luisansal.jetpack.components.edittext

import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.luisansal.jetpack.R
import com.luisansal.jetpack.components.ConstraintSavedInstanceLayout
import com.luisansal.jetpack.databinding.JetpackEdittextBinding
import com.luisansal.jetpack.utils.afterTextChanged
import kotlinx.android.synthetic.main.layout_error_success.view.*
import kotlinx.android.synthetic.main.jetpack_edittext.view.*


class JetpackEditText(context: Context, attrs: AttributeSet?) : ConstraintSavedInstanceLayout(context, attrs) {
    companion object {
        const val PRESSED = "PRESSED"
    }

    var validated: Boolean = false
        set(value) {
            field = value
            etForm?.tag = PRESSED
            if (value)
                setValidateSuccess()
            else
                setValidateError()
        }

    fun validated(validated: Boolean, @StringRes text: Int) {
        _validated(validated, context.getString(text))
    }

    private var onTextChangeListener: ((string: String?) -> Unit)? = null
    private var afterTextChangeListener: ((string: String?) -> Unit)? = null

    fun onTextChangeListener(listener: ((string: String?) -> Unit)?) {
        onTextChangeListener = listener
    }

    fun afterTextChangeListener(listener: ((string: String?) -> Unit)?) {
        afterTextChangeListener = listener
    }

    private fun _validated(validated: Boolean, text: String) {
        if (validated) {
            _infoSuccess = text
        } else {
            _infoError = text
        }
        this.validated = validated
    }

    fun validated(validated: Boolean, text: String) {
        _validated(validated, text)
    }

    var filters: Array<InputFilter> = arrayOf()
        set(value) {
            field = value
            etForm?.filters = value
            invalidateView()
        }
    var isEnable: Boolean = true
        set(value) {
            field = value
            etForm?.isEnabled = value
            invalidateView()
        }
    var inputType: Int = 0
    var text: String? = null
        get() {
            val value = binding.text
            invalidateView()
            return value
        }
        set(value) {
            field = value
            binding.text = value
            invalidateView()
        }

    private var _infoSuccess: String? = null
    private var _infoError: String? = null

    private fun setValidateSuccess() {
        doETSuccess()
        _infoSuccess?.let {
            tvSuccess?.text = it
            tvSuccess?.visibility = View.VISIBLE
        }
        invalidateView()
    }

    private fun setValidateError() {
        doETError()
        _infoError?.let {
            tvError?.text = it
        }
        invalidateView()
    }

    private fun doETSuccess() {
        etForm?.background = ContextCompat.getDrawable(context, R.drawable.ic_shape_rectangle_input_success)
        etForm?.setTextColor(ContextCompat.getColor(context, R.color.success))
        tvSuccess?.visibility = View.GONE
        tvError?.visibility = View.GONE
    }

    private fun doETError() {
        etForm?.background = ContextCompat.getDrawable(context, R.drawable.ic_shape_rectangle_input_error)
        etForm?.setTextColor(ContextCompat.getColor(context, R.color.error))
        tvError?.visibility = View.VISIBLE
        tvSuccess.visibility = View.GONE
    }

    private val binding by lazy {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        JetpackEdittextBinding.inflate(inflater, this, true)
    }

    init {
        context.theme?.obtainStyledAttributes(
            attrs,
            R.styleable.JetpackEditText,
            0, 0
        )?.apply {
            try {

                binding.hint = getString(R.styleable.JetpackEditText_android_hint)
                etForm?.inputType = getInt(R.styleable.JetpackEditText_android_inputType, InputType.TYPE_CLASS_TEXT)
                val textDefault: String? = getString(R.styleable.JetpackEditText_android_text)
                binding.text = textDefault
                etForm?.nextFocusDownId = getInt(R.styleable.JetpackEditText_android_nextFocusDown, View.NO_ID)
                etForm?.nextFocusRightId = getInt(R.styleable.JetpackEditText_android_nextFocusRight, View.NO_ID)
                etForm?.maxLines = getInt(R.styleable.JetpackEditText_android_maxLines, 1)
                etForm?.imeOptions = getInt(R.styleable.JetpackEditText_android_imeOptions, EditorInfo.IME_NULL)
                etForm?.isEnabled = getBoolean(R.styleable.JetpackEditText_android_enabled, true)
                _infoSuccess = getString(R.styleable.JetpackEditText_infoSuccess)
                _infoError = getString(R.styleable.JetpackEditText_infoError)
                etForm?.filters = arrayOf(
                    InputFilter.LengthFilter(
                        getInt(
                            R.styleable.JetpackEditText_android_maxLength,
                            Int.MAX_VALUE
                        )
                    )
                )
            } finally {
                recycle()
            }
        }

        etForm?.addTextChangedListener {
            val string = if (it.toString().isEmpty() && etForm.tag != PRESSED) null else it.toString()
            onTextChangeListener?.invoke(string)
        }
        etForm?.afterTextChanged {
            afterTextChangeListener?.invoke(it)
        }
        invalidateView()
    }

    private fun invalidateView() {
        invalidate()
        requestLayout()
    }
}