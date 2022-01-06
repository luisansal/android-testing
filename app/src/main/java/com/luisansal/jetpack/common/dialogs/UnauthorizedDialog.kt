package com.luisansal.jetpack.common.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.luisansal.jetpack.base.BaseDialog
import com.luisansal.jetpack.data.preferences.AuthSharedPreferences
import com.luisansal.jetpack.databinding.DialogUnauthorizedBinding
import org.koin.android.ext.android.inject

class UnauthorizedDialog : BaseDialog<DialogUnauthorizedBinding>(
    heightParam = ViewGroup.LayoutParams.MATCH_PARENT) {

    private val authPrefRepository: AuthSharedPreferences by inject ()

    override fun getTagNameDialog(): String = DialogUnauthorizedBinding::class.java.simpleName

    override fun setDataFromArguments(args: Bundle) {}

    override fun getViewBinding(inflater: LayoutInflater) = DialogUnauthorizedBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpDialog()
    }

    private fun setUpDialog() {
        mBinding.btnOk.setOnClickListener {
            authPrefRepository.isLogged = false
            requireActivity().finishAffinity()
            dismissDialog()
        }
    }

    companion object{
        fun newInstance() = UnauthorizedDialog()
    }
}