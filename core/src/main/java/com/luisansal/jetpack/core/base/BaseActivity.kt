package com.luisansal.jetpack.core.base

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.luisansal.jetpack.core.dialogs.*
import com.luisansal.jetpack.core.domain.exceptions.*
import com.luisansal.jetpack.core.utils.EMPTY
import pe.com.luisansal.core.R
import java.net.UnknownHostException

/**
 * Created by Luis on 23/2/2016.
 */
abstract class BaseActivity : AppCompatActivity() {

    protected abstract fun getViewIdResource(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getViewIdResource() != -1)
            setContentView(getViewIdResource())
    }

    open fun showMessage(message: String) {
        alertMessage(message)
    }

    open fun showMessage(@StringRes message: Int) {
        alertMessage(getString(message))
    }

    open fun alertMessage(message: String, textBtn: String = String.EMPTY,onClickOk: (() -> Unit)? = null) {
        val errorDialog = AlertDialogFragment.newInstance()
        errorDialog.onClickBtnOk = onClickOk
        errorDialog.subtitle = message
        errorDialog.btnOkText =textBtn
        try {
            errorDialog.show(supportFragmentManager, errorDialog.tag)
        } catch (ex: Exception) {
        }
    }

    open fun showMessageByException(
        exception: Exception,
        onClickOkNetwork: (() -> Unit)? = null,
        onClickOkDialog: (() -> Unit) = { onBackPressed() },
        onClickCloseDialog: (() -> Unit)? = null
    ) {
        when (exception) {
            is UnknownHostException -> {
                ErrorNetworkConnectionDialog.newInstance({ onClickOkNetwork?.invoke() }, { onClickCloseDialog?.invoke() })
                    .showDialog(supportFragmentManager)
            }
            is UnexpectedException -> {
                GenericErrorDialog.newInstance(
                    errorMessage = exception.message,
                    onClickOkButton = onClickOkDialog,
                    onClickCloseButton = { onClickCloseDialog?.invoke() }
                ).showDialog(supportFragmentManager)
            }
            is ServiceErrorException, is SocketTimeoutException, is ConnectException -> {
                GenericErrorDialog.newInstance(
                    onClickOkButton = onClickOkDialog,
                    onClickCloseButton = { onClickCloseDialog?.invoke() }
                ).showDialog(supportFragmentManager)
            }
            is NotFoundException -> {
                ErrorServerDialog.newInstance(onClickOkDialog, { onClickCloseDialog?.invoke() })
                    .showDialog(supportFragmentManager)
            }
            is ErrorLogicServerException -> {
                GenericErrorDialog.newInstance(
                    errorMessage = exception.message,
                    onClickOkButton = { onClickOkDialog.invoke() },
                    onClickCloseButton = { onClickCloseDialog?.invoke() }
                ).showDialog(supportFragmentManager)
            }

            is UnauthorizedException -> {
                UnauthorizedDialog.newInstance().showDialog(supportFragmentManager)
            }

            else -> Log.d("ShowMessageByException", "$exception")
        }
    }

    @SuppressLint("ResourceType")
    open fun showLoading(show: Boolean) {
        findViewById<RelativeLayout>(R.id.loading)?.visibility =
            if (show) View.VISIBLE else View.GONE
    }

    open fun hideKeyboard(view: View?) {
        try {
            val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view!!.windowToken, 0)
        } catch (e: Exception) {
            Log.d("BaseActivity", "hideKeyboard: ", e)
        }
    }

}