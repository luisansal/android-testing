package com.luisansal.jetpack.core.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.luisansal.jetpack.core.base.BaseDialog
import com.luisansal.core.databinding.DialogAppOutdatedBinding
import java.io.Serializable

class AppOutdatedDialog : BaseDialog<DialogAppOutdatedBinding>(
    heightParam = ViewGroup.LayoutParams.MATCH_PARENT
) {

    private lateinit var onClickOkButton: () -> Unit

    override fun getTagNameDialog(): String = DialogAppOutdatedBinding::class.java.simpleName

    override fun setDataFromArguments(args: Bundle) {
        onClickOkButton = args.getSerializable(ARG_CLICK_OK_BUTTON) as () -> Unit
    }

    override fun getViewBinding(inflater: LayoutInflater) = DialogAppOutdatedBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpCalendarView()
    }

    private fun setUpCalendarView() {
        mBinding.btnClose.setOnClickListener { dismissDialog() }
        mBinding.btnOk.setOnClickListener {
            onClickOkButton.invoke()
            dismissDialog()
        }
    }

    companion object {
        private const val ARG_CLICK_OK_BUTTON = "click_ok_button"

        fun newInstance(onClickOkButton: () -> Unit) =
            AppOutdatedDialog().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_CLICK_OK_BUTTON, onClickOkButton as Serializable)
                }
            }
    }
}