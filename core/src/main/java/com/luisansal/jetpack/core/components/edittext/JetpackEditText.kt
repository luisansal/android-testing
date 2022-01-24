package com.luisansal.jetpack.core.components.edittext

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
import com.luisansal.jetpack.core.components.ConstraintSavedInstanceLayout
import com.luisansal.jetpack.core.utils.afterTextChanged
import pe.com.luisansal.core.R
import pe.com.luisansal.core.databinding.JetpackEdittextBinding


class JetpackEditText(context: Context, attrs: AttributeSet?) : ConstraintSavedInstanceLayout(context, attrs) {
    companion object {
        const val PRESSED = "PRESSED"
    }

    var validated: Boolean = false
        set(value) {
            field = value
            binding.etForm?.tag = PRESSED
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
            binding.etForm?.filters = value
            invalidateView()
        }
    var isEnable: Boolean = true
        set(value) {
            field = value
            binding.etForm?.isEnabled = value
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
            binding.tvErrorSuccess.tvSuccess?.text = it
            binding.tvErrorSuccess.tvSuccess?.visibility = View.VISIBLE
        }
        invalidateView()
    }

    private fun setValidateError() {
        doETError()
        _infoError?.let {
            binding.tvErrorSuccess.tvError?.text = it
        }
        invalidateView()
    }

    private fun doETSuccess() {
        binding.etForm?.background = ContextCompat.getDrawable(context, R.drawable.ic_shape_rectangle_input_success)
        binding.etForm?.setTextColor(ContextCompat.getColor(context, R.color.success))
        binding.tvErrorSuccess.tvSuccess?.visibility = View.GONE
        binding.tvErrorSuccess.tvError?.visibility = View.GONE
    }

    private fun doETError() {
        binding.etForm?.background = ContextCompat.getDrawable(context, R.drawable.ic_shape_rectangle_input_error)
        binding.etForm?.setTextColor(ContextCompat.getColor(context, R.color.error))
        binding.tvErrorSuccess.tvError?.visibility = View.VISIBLE
        binding.tvErrorSuccess.tvSuccess.visibility = View.GONE
    }

    private val binding by lazy {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        JetpackEdittextBinding.inflate(inflater, this, true)
    }

    init {
        if (isInEditMode) {
            inflate(context, R.layout.jetpack_edittext, this)
        } else {
            context.theme?.obtainStyledAttributes(
                attrs,
                R.styleable.JetpackEditText,
                0, 0
            )?.apply {
                try {

                    binding.hint = getString(R.styleable.JetpackEditText_android_hint)
                    binding.etForm?.inputType = getInt(R.styleable.JetpackEditText_android_inputType, InputType.TYPE_CLASS_TEXT)
                    val textDefault: String? = getString(R.styleable.JetpackEditText_android_text)
                    binding.text = textDefault
                    binding.etForm?.nextFocusDownId = getInt(R.styleable.JetpackEditText_android_nextFocusDown, View.NO_ID)
                    binding.etForm?.nextFocusRightId = getInt(R.styleable.JetpackEditText_android_nextFocusRight, View.NO_ID)
                    binding.etForm?.maxLines = getInt(R.styleable.JetpackEditText_android_maxLines, 1)
                    binding.etForm?.imeOptions = getInt(R.styleable.JetpackEditText_android_imeOptions, EditorInfo.IME_NULL)
                    binding.etForm?.isEnabled = getBoolean(R.styleable.JetpackEditText_android_enabled, true)
                    _infoSuccess = getString(R.styleable.JetpackEditText_infoSuccess)
                    _infoError = getString(R.styleable.JetpackEditText_infoError)
                    binding.etForm?.filters = arrayOf(
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

            binding.etForm?.addTextChangedListener {
                val string = if (it.toString().isEmpty() && binding.etForm.tag != PRESSED) null else it.toString()
                onTextChangeListener?.invoke(string)
            }
            binding.etForm?.afterTextChanged {
                afterTextChangeListener?.invoke(it)
            }
            invalidateView()
        }
    }

    private fun invalidateView() {
        invalidate()
        requestLayout()
    }
}