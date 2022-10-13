package com.luisansal.jetpack.core.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.luisansal.jetpack.core.base.BaseDialog
import com.luisansal.jetpack.core.databinding.DialogErrorServerBinding
import java.io.Serializable

class ErrorServerDialog : BaseDialog<DialogErrorServerBinding>(
    heightParam = ViewGroup.LayoutParams.MATCH_PARENT
) {

    private lateinit var onClickOkButton: () -> Unit
    private lateinit var onClickCloseButton: () -> Unit

    override fun getTagNameDialog(): String = DialogErrorServerBinding::class.java.simpleName

    override fun setDataFromArguments(args: Bundle) {
        onClickOkButton = args.getSerializable(ARG_CLICK_OK_BUTTON) as () -> Unit
        onClickCloseButton = args.getSerializable(ARG_CLICK_CLOSE_BUTTON) as () -> Unit
    }

    override fun getViewBinding(inflater: LayoutInflater) = DialogErrorServerBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpCalendarView()
    }

    private fun setUpCalendarView() {
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

        fun newInstance(onClickOkButton: () -> Unit, onClickCloseButton: () -> Unit) =
            ErrorServerDialog().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_CLICK_OK_BUTTON, onClickOkButton as Serializable)
                    putSerializable(ARG_CLICK_CLOSE_BUTTON, onClickCloseButton as Serializable)
                }
            }
    }
}