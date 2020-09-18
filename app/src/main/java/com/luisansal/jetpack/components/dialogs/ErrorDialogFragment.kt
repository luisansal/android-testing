package com.luisansal.jetpack.components.dialogs

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.luisansal.jetpack.R
import kotlinx.android.synthetic.main.dialog_error.*

class ErrorDialogFragment : DialogFragment() {

    companion object {
        fun newInstance() = ErrorDialogFragment()
    }

    var message = ""
    var onClickBtnOk : (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.Theme_MaterialComponents_Light_Dialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_error, container, false)
    }

    private fun onClickBtnOk(){
        btnOk.setOnClickListener {
            onClickBtnOk?.invoke()
            dismiss()
        }
    }

    private fun onClickBtnCancel(){
        btnCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.shape_dialog_document);
        clContainerError.setOnClickListener { dismissAllowingStateLoss() }
        tvMessage.text = message
        onClickBtnOk()
        onClickBtnCancel()
    }
}