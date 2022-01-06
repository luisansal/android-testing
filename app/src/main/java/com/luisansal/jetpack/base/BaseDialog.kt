package com.luisansal.jetpack.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding

abstract class BaseDialog<B:ViewBinding>(
        private val widthParam: Int = ViewGroup.LayoutParams.MATCH_PARENT,
        private val heightParam: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
        private val cancelable: Boolean = false,
        private val cancelableOnTouchOutside: Boolean = false,
        private val gravity: Int = Gravity.NO_GRAVITY): DialogFragment() {

    protected lateinit var mBinding: B
    private val nameDialog = "dialog"

    abstract fun getTagNameDialog(): String

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =  super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args -> setDataFromArguments(args) }
    }

    abstract fun setDataFromArguments(args: Bundle)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = getViewBinding(inflater)
        return mBinding.root
    }

    abstract fun getViewBinding(inflater: LayoutInflater): B

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