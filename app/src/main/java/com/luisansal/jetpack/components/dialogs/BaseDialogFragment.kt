package com.luisansal.jetpack.components.dialogs

import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.luisansal.jetpack.R

open class BaseDialogFragment: DialogFragment()  {

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        isCancelable = false
    }

}