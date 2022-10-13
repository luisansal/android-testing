package com.luisansal.jetpack.core.dialogs

import android.os.Bundle
import android.view.*
import androidx.annotation.DrawableRes
import com.luisansal.jetpack.core.base.BaseDialogFragment
import com.luisansal.jetpack.core.databinding.FragmentJetpackDialogBinding

sealed class BtnColors {
    object BtnRed : BtnColors()
    object BtnGreen : BtnColors()
}

open class JetpackDialogFragment : BaseDialogFragment() {

    companion object {
        fun newInstance() =
            JetpackDialogFragment()
    }

    private lateinit var binding: FragmentJetpackDialogBinding

    @DrawableRes
    var image = 0
    var title = ""
    var subtitle = ""
    var btnText = ""
    var btnColor : BtnColors? = null

    var onClickBtnOk: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJetpackDialogBinding.inflate(inflater).apply {
            lifecycleOwner = this@JetpackDialogFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.image = image
        binding.title = title
        binding.subtitle = subtitle
        binding.btnOkGreen.text = btnText
        binding.btnOkRed.text = btnText
        btnColor?.let {
            when(it){
                is BtnColors.BtnRed ->{
                    binding.btnOkRed.visibility = View.VISIBLE
                    binding.btnOkGreen.visibility = View.GONE
                }
                is BtnColors.BtnGreen ->{
                    binding.btnOkGreen.visibility = View.VISIBLE
                    binding.btnOkRed.visibility = View.GONE
                }
            }
        }

        onClickBtnOk()
    }

    private fun onClickBtnOk() {
        binding.onClickOk = View.OnClickListener {
            onClickBtnOk?.invoke()
            dismiss()
        }
    }
}