package com.luisansal.jetpack.core.base_new

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseBindingFragment<V: ViewDataBinding> : Fragment() {

    protected lateinit var binding: V

    abstract fun onInflate(inflater: LayoutInflater): V

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = onInflate(inflater).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    fun getBaseActivity() = (requireActivity() as BaseBindingActivity<V>)
}
