package com.luisansal.jetpack.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.luisansal.jetpack.R
import com.luisansal.jetpack.components.dialogs.ErrorDialogFragment

abstract class BaseFragment : Fragment() {

    protected abstract fun getViewIdResource(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getViewIdResource(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    open fun showLoading(show: Boolean) {
        view?.findViewById<FrameLayout>(R.id.loading)?.visibility = if (show) View.VISIBLE else View.GONE
    }

    open fun showMessage(message: String) {
        alertMessage(message)
    }

    open fun showMessage(@StringRes message: Int) {
        alertMessage(getString(message))
    }

    private fun alertMessage(message: String) {
        val errorDialog = ErrorDialogFragment.newInstance()
        errorDialog.message = message
        errorDialog.show(parentFragmentManager, errorDialog.tag)
    }

    open fun showSessionCloseMessage(@StringRes message: Int) {
        alertMessage(getString(message))
    }
}