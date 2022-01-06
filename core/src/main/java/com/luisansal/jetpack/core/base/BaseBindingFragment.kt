package com.luisansal.jetpack.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseBindingFragment : BaseFragment() {

    protected abstract fun getViewResource(): View
    override fun getViewIdResource() = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return getViewResource()
    }
}
