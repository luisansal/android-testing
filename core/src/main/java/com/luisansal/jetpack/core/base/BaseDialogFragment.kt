package com.luisansal.jetpack.core.base

import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.luisansal.core.R

open class BaseDialogFragment: DialogFragment()  {
    private val nameDialog = "dialog"
    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        isCancelable = false
    }

    fun showDialog(fragmentManager: FragmentManager){
        val dialogExist = fragmentManager.findFragmentByTag(nameDialog) == null
        if (dialogExist)
            show(fragmentManager,nameDialog)
    }
}