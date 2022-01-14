package com.luisansal.jetpack.core.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.luisansal.jetpack.core.base.BaseDialogFragment
import pe.com.luisansal.core.R
import pe.com.luisansal.core.databinding.FragmentDialogAlertBinding
import java.io.Serializable

class AlertDialogFragment : BaseDialogFragment() {

    companion object {
        private const val TITLE = "TITLE"
        private const val SUBTITLE = "SUBTITLE"
        private const val BTN_OK_TEXT = "BTN_OK_TEXT"
        private const val ON_CLICK_OK = "ON_CLICK_OK"
        fun newInstance(title: String = "", subtitle: String, btnOkText: String, onClickBtnOk: (() -> Unit)? = null) = AlertDialogFragment().apply {
            arguments = Bundle().apply {
                putString(TITLE, title)
                putString(SUBTITLE, subtitle)
                putString(BTN_OK_TEXT, btnOkText)
                putSerializable(ON_CLICK_OK, onClickBtnOk as Serializable?)
            }
        }
    }

    private val title by lazy { arguments?.getString(TITLE) }
    private val subtitle by lazy { arguments?.getString(SUBTITLE) }
    private val btnOkText by lazy { arguments?.getString(BTN_OK_TEXT) }
    private val _onClickBtnOk by lazy { arguments?.getSerializable(ON_CLICK_OK) as (() -> Unit)? }

    private lateinit var binding: FragmentDialogAlertBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_alert, container, false)
        return binding.root
    }

    private fun onClickBtnOk() {
        binding.onClickOk = View.OnClickListener {
            _onClickBtnOk?.invoke()
            dismiss()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title = title
        binding.subtitle = subtitle
        binding.btnOkText = btnOkText
        onClickBtnOk()
    }
}