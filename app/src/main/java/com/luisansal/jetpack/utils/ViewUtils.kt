package com.luisansal.jetpack.utils

import android.animation.Animator
import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout


fun View.hideKeyboardFrom(context: Context) {
    val imm: InputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.getWindowToken(), 0)
}

fun TabLayout.disableTouchTabs (boolean : Boolean= true){
    val tabStrip = this.getChildAt(0) as LinearLayout
    for (i in 0 until tabStrip.getChildCount()) {
        tabStrip.getChildAt(i).setOnTouchListener { v, event -> boolean }
    }
}

fun TabLayout.enableTouchTabs (){
    this.disableTouchTabs(false)
}

fun TabLayout.Tab.disableTouch(boolean: Boolean = true){
    this.view.setOnTouchListener { view, motionEvent ->
        boolean
    }
}

fun TabLayout.Tab.enableTouch(){
    this.disableTouch(false)
}

fun ViewPager.disableSwipe(boolean: Boolean = true){
    this.setOnTouchListener { view, motionEvent ->  boolean }
}

fun ViewPager.enableSwipe(){
    this.disableSwipe(false)
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

fun View?.animateToUp(height: Float) {
    if (this?.visibility == View.GONE) {
        this.animate().apply {
            translationY(height)
            alpha(0.0f)
            duration = 0
            setListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(p0: Animator?) = Unit
                override fun onAnimationEnd(p0: Animator?) {
                    this@animateToUp.visibility = View.VISIBLE
                    this@animateToUp.animate()?.apply {

                        translationY(0f)
                        alpha(1.0f)
                        duration = 600
                    }
                }

                override fun onAnimationCancel(p0: Animator?) = Unit
                override fun onAnimationStart(p0: Animator?) = Unit
            })
        }
    }
}