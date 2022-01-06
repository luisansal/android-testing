package com.luisansal.jetpack.common.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.luisansal.jetpack.base.BaseDialog
import com.luisansal.jetpack.databinding.DialogPermissionsAutomaticallyBinding
import java.io.Serializable

class PermissionsAutoDialog : BaseDialog<DialogPermissionsAutomaticallyBinding>() {
    private lateinit var onClickContinueButton: () -> Unit
    private var onClickCancelButton: (() -> Unit)? = null

    override fun getTagNameDialog(): String = DialogPermissionsAutomaticallyBinding::class.java.simpleName

    override fun setDataFromArguments(args: Bundle) {
        onClickContinueButton = args.getSerializable(ARG_CLICK_CONTINUE_BUTTON) as () -> Unit
        onClickCancelButton = args.getSerializable(ARG_CANCEL_BUTTON) as (() -> Unit)?
    }

    override fun getViewBinding(inflater: LayoutInflater) = DialogPermissionsAutomaticallyBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpDialog()
    }

    private fun setUpDialog() {
        mBinding.btnCancel.setOnClickListener {
            if (onClickCancelButton != null) {
                onClickCancelButton!!.invoke()
            } else {
                dismissDialog()
            }
        }

        mBinding.btnOk.setOnClickListener {
            onClickContinueButton.invoke()
            dismissDialog()
        }
    }

    companion object {
        private const val ARG_CLICK_CONTINUE_BUTTON = "ARG_CLICK_CONTINUE_BUTTON"
        private const val ARG_CANCEL_BUTTON = "ARG_CANCEL_BUTTON"

        fun newInstance(onClickContinueButton: () -> Unit, onClickCancelButton: (() -> Unit)? = null) =
            PermissionsAutoDialog().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_CLICK_CONTINUE_BUTTON, onClickContinueButton as Serializable)
                    onClickCancelButton?.also {
                        putSerializable(ARG_CANCEL_BUTTON, onClickCancelButton as Serializable)
                    }
                }
            }
    }
}