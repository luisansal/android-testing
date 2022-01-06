package com.luisansal.jetpack.common.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.luisansal.jetpack.base.BaseDialog
import com.luisansal.jetpack.databinding.DialogPermissionsManuallyBinding
import java.io.Serializable

class PermissionsManualDialog : BaseDialog<DialogPermissionsManuallyBinding>() {
    private lateinit var onClickSettingsButton: () -> Unit
    private var onClickCancelButton: (() -> Unit)? = null

    override fun getTagNameDialog(): String = DialogPermissionsManuallyBinding::class.java.simpleName

    override fun setDataFromArguments(args: Bundle) {
        onClickSettingsButton = args.getSerializable(ARG_CLICK_SETTINGS_BUTTON) as () -> Unit
        onClickCancelButton = args.getSerializable(ARG_CANCEL_BUTTON) as (() -> Unit)?
    }

    override fun getViewBinding(inflater: LayoutInflater) = DialogPermissionsManuallyBinding.inflate(inflater)

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
            onClickSettingsButton.invoke()
            dismissDialog()
        }
    }

    companion object {
        private const val ARG_CLICK_SETTINGS_BUTTON = "ARG_CLICK_SETTINGS_BUTTON"
        private const val ARG_CANCEL_BUTTON = "ARG_CANCEL_BUTTON"

        fun newInstance(onClickSettingsButton: () -> Unit, onClickCancelButton: (() -> Unit)? = null) =
            PermissionsManualDialog().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_CLICK_SETTINGS_BUTTON, onClickSettingsButton as Serializable)
                    onClickCancelButton?.also {
                        putSerializable(ARG_CANCEL_BUTTON, onClickCancelButton as Serializable)
                    }
                }
            }
    }
}