package com.luisansal.jetpack.components.dialogs

import android.os.Bundle
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.luisansal.jetpack.R
import com.luisansal.jetpack.utils.toSp
import kotlinx.android.synthetic.main.dialog_alert.*

class AlertDialogFragment : BaseDialogFragment() {

    companion object {
        fun newInstance() = AlertDialogFragment()
    }

    var title = ""
    var message = ""
    var onClickBtnOk: (() -> Unit)? = null
    var isVisibleCancelBtn: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_alert, container, false)
    }

    private fun onClickBtnOk() {
        btnOk?.setOnClickListener {
            onClickBtnOk?.invoke()
            dismiss()
        }
    }

    private fun onClickBtnCancel() {
        btnCancel?.setOnClickListener {
            dismiss()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tvTitle?.visibility = View.GONE
        tvMessage?.text = message
        if (message.length > 30)
            tvMessage.textSize = 6.toSp(requireContext())

        if (!title.equals("")) {
            tvTitle?.text = title
            tvTitle?.visibility = View.VISIBLE
            tvMessage?.textSize = 6.toSp(requireContext())
        }

        if (!isVisibleCancelBtn) {
            val layoutParams = btnOk.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            btnOk?.layoutParams = layoutParams
        }
        btnCancel?.visibility = if (isVisibleCancelBtn) View.VISIBLE else View.GONE

        onClickBtnOk()
        onClickBtnCancel()
    }
}