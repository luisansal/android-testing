package com.luisansal.jetpack.core.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.luisansal.jetpack.core.base.BaseDialog
import pe.com.luisansal.core.R
import pe.com.luisansal.core.databinding.DialogGenericErrorBinding
import java.io.Serializable

class GenericErrorDialog : BaseDialog<DialogGenericErrorBinding>(
    heightParam = ViewGroup.LayoutParams.MATCH_PARENT
) {

    private lateinit var onClickOkButton: () -> Unit
    private lateinit var onClickCloseButton: () -> Unit
    private lateinit var errorMessage: String

    override fun getTagNameDialog(): String = DialogGenericErrorBinding::class.java.simpleName

    override fun setDataFromArguments(args: Bundle) {
        onClickOkButton = args.getSerializable(ARG_CLICK_OK_BUTTON) as () -> Unit
        onClickCloseButton = args.getSerializable(ARG_CLICK_CLOSE_BUTTON) as () -> Unit
        errorMessage = args.getString(ARG_ERROR_MESSAGE, getString(R.string.generic_error_message))
    }

    override fun getViewBinding(inflater: LayoutInflater) = DialogGenericErrorBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpDialog()
    }

    private fun setUpDialog() {
        mBinding.tvErrorMessage.text = errorMessage
        mBinding.btnClose.setOnClickListener {
            onClickCloseButton.invoke()
            dismissDialog()
        }
        mBinding.btnOk.setOnClickListener {
            onClickOkButton.invoke()
            dismissDialog()
        }
    }

    companion object {
        private const val ARG_CLICK_OK_BUTTON = "click_ok_button"
        private const val ARG_CLICK_CLOSE_BUTTON = "ARG_CLICK_CLOSE_BUTTON"
        private const val ARG_ERROR_MESSAGE = "error_message"

        fun newInstance(errorMessage: String? = null, onClickOkButton: () -> Unit, onClickCloseButton: () -> Unit) =
            GenericErrorDialog().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_CLICK_OK_BUTTON, onClickOkButton as Serializable)
                    putSerializable(ARG_CLICK_CLOSE_BUTTON, onClickCloseButton as Serializable)
                    putString(ARG_ERROR_MESSAGE, errorMessage)
                }
            }
    }
}