package com.luisansal.jetpack.core.base

import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import pe.com.luisansal.core.R

open class BaseDialogFragment: DialogFragment()  {

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        isCancelable = false
    }

}