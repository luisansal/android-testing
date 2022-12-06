package com.luisansal.jetpack.core.base_new

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.luisansal.jetpack.core.R
import com.luisansal.jetpack.core.dialogs.*
import com.luisansal.jetpack.core.domain.exceptions.*
import com.luisansal.jetpack.core.utils.EMPTY

abstract class BaseBindingActivity<V: ViewDataBinding> : AppCompatActivity() {
    protected lateinit var binding: V
    protected abstract fun onInflate(inflater: LayoutInflater): V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = onInflate(layoutInflater).apply {
            lifecycleOwner = lifecycleOwner
        }
        setContentView(binding.root)
    }

    open fun showMessage(message: String) {
        alertMessage(message)
    }

    open fun showMessage(@StringRes message: Int) {
        alertMessage(getString(message))
    }

    open fun alertMessage(message: String, textBtn: String = EMPTY, onClickOk: (() -> Unit)? = null) {
        AlertDialog.newInstance(
            subtitle = message,
            btnOkText = textBtn,
            onClickBtnOk = onClickOk
        ).showDialog(supportFragmentManager)
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
                ErrorServerDialog.newInstance(onClickOkDialog) { onClickCloseDialog?.invoke() }
                    .showDialog(supportFragmentManager)
            }
            is NotFoundException -> {
                GenericErrorDialog.newInstance(
                    errorMessage = exception.message,
                    onClickOkButton = onClickOkDialog,
                    onClickCloseButton = { onClickCloseDialog?.invoke() }
                ).showDialog(supportFragmentManager)
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
}
