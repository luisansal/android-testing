package com.luisansal.jetpack.core.utils

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.os.Build
import android.text.Html
import android.text.InputFilter
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.luisansal.jetpack.core.R
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("bitmapImage")
fun ImageView.setImageFromBitmap(bitmapImage: Bitmap?) {
    bitmapImage?.let {
        setImageBitmap(it)
    }
}

@BindingAdapter("enableGreenButton")
fun Button.setButtonGreenEnabled(value: Boolean?) {
    value?.let { enable ->
        if (enable) {
            backgroundTintList =
                ContextCompat.getColorStateList(context.applicationContext, R.color.green_dark)
            setTextColor(resources.getColor(R.color.white))
            isEnabled = true
        } else {
            backgroundTintList = ContextCompat.getColorStateList(
                context.applicationContext,
                R.color.button_disabled_color
            )
            setTextColor(resources.getColor(R.color.button_disabled_text_color))
            isEnabled = false
        }
    }
}


@BindingAdapter("changeButtonIcon", "esError")
fun ImageView.setShowIcon(mostrar: Boolean?, esError: Boolean?) {
    mostrar?.also { show ->
        val imagen = if (show) R.drawable.ic_showpass else R.drawable.ic_hide
        esError?.also {
            val color = if (it) {
                ContextCompat.getColor(context.applicationContext, R.color.red_normal)
            } else {
                ContextCompat.getColor(context.applicationContext, R.color.green_dark)
            }
            ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(color))
        }
        setImageResource(imagen)

    }
}

@BindingAdapter("showTextPassword")
fun EditText.setShowTextPassword(showTextPassword: Boolean?) {
    showTextPassword?.also { show ->
        transformationMethod =
            if (show) HideReturnsTransformationMethod.getInstance() else PasswordTransformationMethod.getInstance()
        setSelection(this.text.length)
    }
}


@BindingAdapter("passwordErrorType")
fun AppCompatTextView.setPasswordErrorType(passwordErrorType: Int?) {
    passwordErrorType?.also { tipo ->
        when (tipo) {
            1 -> text = resources.getText(R.string.password_not_valid)
            2 -> text = resources.getText(R.string.password_format_not_valid)
            3 -> text = resources.getText(R.string.password_not_match)
        }
    }
}

@BindingAdapter("selectCurrency")
fun MaterialButton.setSelectCurrency(value: Boolean?) {
    value?.let { enable ->
        if (enable) {
            backgroundTintList =
                ContextCompat.getColorStateList(context.applicationContext, R.color.green_light_one)
            iconTint = ContextCompat.getColorStateList(context.applicationContext, R.color.white)
            setTextColor(resources.getColor(R.color.white))
        } else {
            backgroundTintList = ContextCompat.getColorStateList(
                context.applicationContext,
                R.color.button_disabled_color
            )
            iconTint = ContextCompat.getColorStateList(
                context.applicationContext,
                R.color.button_disabled_text_color
            )
            setTextColor(resources.getColor(R.color.button_disabled_text_color))
        }
    }
}

@BindingAdapter("firstName", "secondName")
fun MaterialTextView.setFullName(firstName: String?, secondName: String?) {
    text = if (firstName.isNullOrEmpty() && secondName.isNullOrEmpty()) "Sin nombre"
    else "$firstName $secondName"
}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("fullDate")
fun TextView.setDate(value: String?) {
    value?.let { strDateFull ->
        val myDate = SimpleDateFormat(FORMAT_DATE_yyyyMMdd_HHmmss_DASH)
        myDate.timeZone = TimeZone.getTimeZone("GMT")
        val newDate = myDate.parse(strDateFull)
        val dateFormat = SimpleDateFormat(FORMAT_DATE_ddMMyyyy_HHmm_SLASH)
        val strDate = dateFormat.format(newDate)
        text = strDate
    }
}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("fullDateWithText")
fun TextView.setDateWithText(value: String?) {
    value?.let { strDateFull ->
        this.setDate(strDateFull)
        val newText = this.text.toString().replace("-","a las")
        this.text = newText
    }
}


@BindingAdapter("amount", "currency")
fun TextView.setAmountWithCurrency(amount: Double?, currency: String?) {
    val currencyValue = currency ?: resources.getString(R.string.currency_pen)
    val symbolCurrency =
        if (currencyValue == resources.getString(R.string.currency_pen)) PEN_SYMBOL else USD_SYMBOL
    text = "$symbolCurrency ${amount?.formaterNumberDecimal(2) ?: "0.00"}"
}

@BindingAdapter("requestFocus")
fun requestFocus(view: EditText, requestFocus: Boolean?) {
    requestFocus?.also {
        if (it) {
            view.requestFocus()
            view.showKeyboard()
        }
    }
}

@BindingAdapter("tipAmount", "amount", "currency")
fun setAmountWithCurrencyWithTip(view: TextView, tipAmount: Double?, amount: Double?, currency: String?) {
    val totalAmount = amount?.plus(tipAmount ?: 0.0)
    val currencyValue = currency ?: view.resources.getString(R.string.currency_pen)
    val symbolCurrency =
        if (currencyValue == view.resources.getString(R.string.currency_usd)) USD_SYMBOL else PEN_SYMBOL
    view.text = "$symbolCurrency ${totalAmount?.formaterNumberDecimal(2) ?: "0.00"}"
}


@BindingAdapter("amountNoCurrency")
fun TextView.setAmountNoCurrency(amount: Double?) {
    val symbolCurrency = PEN_SYMBOL
    text = "$symbolCurrency ${amount?.formaterNumberDecimal(2) ?: "0.00"}"
}

@BindingAdapter("codeResultText")
fun TextView.setCodeResultText(value: Boolean?) {
    value?.let {
        when (value) {
            true -> {
                val html = "<p><span style='color:#00A09D'>Muestra o comparte este QR</span> con tu</p>" +
                        "<p>cliente para que lo escanee y <span style='color:#00A09D'>pague</span></p>" +
                        "<p style='color:#00A09D'>con una billetera móvil.</p>"
                text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT)
                } else {
                    Html.fromHtml(html)
                }
                setTextColor(resources.getColor(R.color.gray_text_dark))
            }
            else -> {
                text = "El código no fue generado. Por favor, inténtalo nuevamente."
                setTextColor(resources.getColor(R.color.red_normal))
            }
        }
    }
}

@BindingAdapter("isVisibleView")
fun View.setVisibleView(value: Boolean?) {
    value?.let { visible ->
        visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }
}

@BindingAdapter("isShowView")
fun View.setShowView(value: Boolean?) {
    value?.let { visible ->
        visibility = if (visible) View.VISIBLE else View.GONE
    }
}

@BindingAdapter("viewinputColor")
fun View.setColorFromInput(viewinputColor: Int?) {
    viewinputColor?.also {
        when (it) {
            NORMAL_INPUT_COLOR -> {
                background =
                    ResourcesCompat.getDrawable(resources, R.drawable.bg_input_normal, null)
            }
            VALID_INPUT_COLOR -> {
                background = ResourcesCompat.getDrawable(resources, R.drawable.bg_input_valid, null)
            }
            INVALID_INPUT_COLOR -> {
                background = ResourcesCompat.getDrawable(resources, R.drawable.bg_input_error, null)
            }
        }
    }
}

@BindingAdapter("inputColorNoBackground")
fun EditText.setColorInputNoBackground(inputColorNoBackground: Int?) {
    inputColorNoBackground?.also {
        when (it) {
            NORMAL_INPUT_COLOR -> {
                setBackgroundResource(0)
                setHintTextColor(resources.getColor(R.color.gray_text_normal))
                setTextColor(resources.getColor(R.color.gray_text_normal))
            }
            VALID_INPUT_COLOR -> {
                setBackgroundResource(R.drawable.bg_input_valid)
                setHintTextColor(resources.getColor(R.color.hint_text_color))
                setTextColor(resources.getColor(R.color.green_dark))
            }
            INVALID_INPUT_COLOR -> {
                setBackgroundResource(R.drawable.bg_input_error)
                setHintTextColor(resources.getColor(R.color.red_normal))
                setTextColor(resources.getColor(R.color.red_normal))
            }
        }
    }
}


@BindingAdapter("minVal", "maxVal")
fun EditText.setFilterValue(minVal: Int, maxVal: Int) {
    filters = arrayOf<InputFilter>(MinMaxFilter(minVal, maxVal))
}

@BindingAdapter( "filterMaxLength")
fun EditText.setFilterMaxLengthValue(max: Int) {
    filters = arrayOf<InputFilter>(InputFilter.LengthFilter(max))
}

@BindingAdapter("inputColor")
fun EditText.setColorInput(inputColor: Int?) {
    inputColor?.let {
        when (it) {
            NORMAL_INPUT_COLOR -> {
                setBackgroundResource(R.drawable.bg_input_normal)
                setHintTextColor(resources.getColor(R.color.hint_text_color))
                setTextColor(resources.getColor(R.color.gray_text_normal))
            }
            VALID_INPUT_COLOR -> {
                setBackgroundResource(R.drawable.bg_input_valid)
                setHintTextColor(resources.getColor(R.color.hint_text_color))
                setTextColor(resources.getColor(R.color.green_dark))
            }
            INVALID_INPUT_COLOR -> {
                setBackgroundResource(R.drawable.bg_input_error)
                setHintTextColor(resources.getColor(R.color.red_normal))
                setTextColor(resources.getColor(R.color.red_normal))
            }
        }
    }
}

@BindingAdapter("toggleColor")
fun TextInputLayout.setColorTogglePassword(inputColor: Int?) {
    inputColor?.let {
        when (it) {
            INVALID_INPUT_COLOR -> {
                setEndIconTintList(ContextCompat.getColorStateList(context,R.color.red_600))
            }
            NORMAL_INPUT_COLOR -> {
                setEndIconTintList(ContextCompat.getColorStateList(context,R.color.green_dark))
            }
            VALID_INPUT_COLOR -> {
                setEndIconTintList(ContextCompat.getColorStateList(context,R.color.green_dark))
            }
        }
    }
}

@BindingAdapter("inputColor")
fun TextView.setColorInput(inputColor: Int?) {
    inputColor?.let {
        when (it) {
            NORMAL_INPUT_COLOR -> {
                setBackgroundResource(R.drawable.bg_input_normal)
                setHintTextColor(resources.getColor(R.color.gray_text_normal))
                setTextColor(resources.getColor(R.color.gray_text_normal))
            }
            VALID_INPUT_COLOR -> {
                setBackgroundResource(R.drawable.bg_input_valid)
                setHintTextColor(resources.getColor(R.color.hint_text_color))
                setTextColor(resources.getColor(R.color.green_dark))
            }
            INVALID_INPUT_COLOR -> {
                setBackgroundResource(R.drawable.bg_input_error)
                setHintTextColor(resources.getColor(R.color.red_normal))
                setTextColor(resources.getColor(R.color.red_normal))
            }
        }
    }
}

@BindingAdapter("isVisibleError")
fun TextView.setVisibleErrorInput(isVisibleError: Int?) {
    isVisibleError?.let {
        visibility = when (it) {
            INVALID_INPUT_COLOR -> View.VISIBLE
            else -> View.GONE
        }
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("storeName","codeCommerce")
fun MaterialTextView.setStoreName(storeName:String?,codeCommerce:String?){
    text = "$storeName ($codeCommerce)"
}

@SuppressLint("SetTextI18n")
@BindingAdapter("address","district")
fun MaterialTextView.setStoreAddress(address:String?,district:String?){
    text = "$address - $district"
}

@BindingAdapter("app:loadImage")
fun loadImage(view: ImageView?, @DrawableRes imageId: Int) {
    view?.setImageResource(imageId)
}

@BindingAdapter("app:loadAdapter")
fun loadAdapter(view: ViewPager?, adapter: FragmentPagerAdapter?){
    adapter?.also {
        view?.adapter = adapter
    }
}

@BindingAdapter("show")
fun show(view: TextView, isShow: Boolean?) {
    isShow?.let {
        if (it)
            view.visibility = View.VISIBLE
        else
            view.visibility = View.GONE
    }
}

@BindingAdapter("adapter")
fun show(view: ViewPager, adapter: FragmentStatePagerAdapter?) {
    view.adapter = adapter
}

@BindingAdapter("toLowerCase")
fun toLowerCase(view: EditText, isLowerCase: Boolean?) {
    isLowerCase?.let {
        if(it){
            view.filters = arrayOf<InputFilter>(AllLowerInputFilter())
        } else {
            view.filters = null
        }
    }
}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("filterDate")
fun MaterialTextView.setDateFiltered(filterDate: Date?){
    filterDate?.let { date ->
        text = SimpleDateFormat(FORMAT_DATE_ddMMyyyy_SLASH).format(date)
    }
}

@BindingAdapter("validateTextColor")
fun TextView.validateTextColor(color: Boolean?){
    when(color){
        null -> this.setTextColor(ContextCompat.getColor(this.context, R.color.gray_text_normal))
        true -> this.setTextColor(ContextCompat.getColor(this.context, R.color.gray_text_normal))
        false -> this.setTextColor(ContextCompat.getColor(this.context, R.color.red_main))
    }
}