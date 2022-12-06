package com.luisansal.jetpack.core.base_new

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

abstract class BaseDialog<B: ViewDataBinding>(
    private val widthParam: Int = ViewGroup.LayoutParams.MATCH_PARENT,
    private val heightParam: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    private val cancelable: Boolean = false,
    private val cancelableOnTouchOutside: Boolean = false,
    private val gravity: Int = Gravity.NO_GRAVITY): DialogFragment() {

    protected lateinit var binding: B
    private val nameDialog = "dialog"

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =  super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = onInflate(inflater).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    abstract fun onInflate(inflater: LayoutInflater): B

    override fun onStart() {
        super.onStart()
        dialog?.let {d ->
            d.setCancelable(cancelable)
            d.setCanceledOnTouchOutside(cancelableOnTouchOutside)
            d.window?.let {
                it.setLayout(widthParam, heightParam)
                it.setGravity(gravity)
            }
        }
    }

    fun showDialog(fragmentManager: FragmentManager){
        val dialogExist = fragmentManager.findFragmentByTag(nameDialog) == null
        if (dialogExist)
            show(fragmentManager,nameDialog)
    }

    fun dismissDialog(){
        dialog?.let {
            if (it.isShowing) dismiss()
        }
    }

    override fun onPause() {
        super.onPause()
        dismissDialog()
    }
}