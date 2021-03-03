package com.luisansal.jetpack.base

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity

import com.luisansal.jetpack.R
import com.luisansal.jetpack.components.dialogs.AlertDialogFragment

/**
 * Created by Luis on 23/2/2016.
 */
abstract class BaseActivity : AppCompatActivity() {

    @JvmOverloads
    fun customActionBar(titulo: String? = null, subtitulo: String? = null) {
        val mActionBar = supportActionBar
        mActionBar?.setDisplayShowHomeEnabled(false)
        mActionBar?.setDisplayShowTitleEnabled(false)
        val mInflater = LayoutInflater.from(this)
        val customView = mInflater.inflate(R.layout.actionbar_item_row, null)
        val back = customView.findViewById<View>(R.id.back) as ImageView
        back.setOnClickListener { onBackPressed() }
        //        back.setVisibility(View.INVISIBLE)
        val textTitulo = customView.findViewById<View>(R.id.titulo) as TextView
        textTitulo.text = titulo

        mActionBar?.customView = customView

        mActionBar?.setDisplayShowCustomEnabled(true)

        val parent = customView.parent as Toolbar
        parent.setContentInsetsAbsolute(0, 0)
    }

    protected abstract fun getViewIdResource(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getViewIdResource() != -1)
            setContentView(getViewIdResource())
    }

    fun showMessage(message: String) {
        alertMessage(message)
    }

    fun showMessage(@StringRes message: Int) {
        alertMessage(getString(message))
    }

    open fun alertMessage(message: String, onClickOk: (() -> Unit)? = null) {
        val errorDialog = AlertDialogFragment.newInstance()
        errorDialog.onClickBtnOk = onClickOk
        errorDialog.isVisibleCancelBtn = false
        errorDialog.message = message
        try {
            errorDialog.show(supportFragmentManager, errorDialog.tag)
        } catch (ex: Exception) {
        }
    }

    open fun showSessionCloseMessage(@StringRes message: Int) {
        alertMessage(getString(message))
    }

    @SuppressLint("ResourceType")
    fun showLoading(show: Boolean) {
        findViewById<FrameLayout>(R.id.loading)?.visibility =
                if (show) View.VISIBLE else View.GONE
    }

    fun hideKeyboard(view: View?) {
        try {
            val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view!!.windowToken, 0)
        } catch (e: Exception) {
            Log.d("BaseActivity", "hideKeyboard: ", e)
        }
    }

}
