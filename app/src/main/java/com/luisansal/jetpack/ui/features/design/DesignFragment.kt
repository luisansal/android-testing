package com.luisansal.jetpack.ui.features.design

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.luisansal.jetpack.R
import com.luisansal.jetpack.common.interfaces.TitleListener

class DesignFragment : Fragment(), TitleListener {

    companion object {
        fun newInstance() = DesignFragment()
    }

    private lateinit var viewModel: DesignViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.design_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DesignViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override val title: String
        get() = "Design System"

}
