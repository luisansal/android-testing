package com.luisansal.jetpack.ui.features.manageusers

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.luisansal.jetpack.R
import com.luisansal.jetpack.common.interfaces.ActionsViewPagerListener
import com.luisansal.jetpack.common.interfaces.CrudListener
import com.luisansal.jetpack.domain.entity.User
import com.luisansal.jetpack.data.repository.UserRepository
import com.luisansal.jetpack.domain.analytics.TagAnalytics
import com.luisansal.jetpack.ui.features.analytics.FirebaseAnalyticsPresenter
import kotlinx.android.synthetic.main.fragment_new_user.*
import org.koin.android.ext.android.inject

class NewUserFragment : Fragment() {

    private val mUserRepository: UserRepository by inject()

    private val firebaseAnalyticsPresenter: FirebaseAnalyticsPresenter by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO: Use the ViewModel
        val user = mCrudListener!!.oBject
        if (user != null) {
            tvResultado!!.text = user.name + " " + user.lastName
            etNombre!!.setText(user.name)
            etApellido!!.setText(user.lastName)
        }
        onClickBtnSiguiente()
        onClickBtnListado()
        onTextDniChanged()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ActionsViewPagerListener) {
            mActivityListener = context
        } else {
            throw RuntimeException(context.toString()
                    + " must implement " + mActivityListener!!.javaClass.getSimpleName())
        }
    }

    override fun onDetach() {
        super.onDetach()
        mActivityListener = null
    }

    private fun onClickBtnSiguiente() {
        btnSiguiente?.setOnClickListener {
            val user = User()
            user.name = etNombre!!.text.toString()
            user.lastName = etApellido!!.text.toString()
            user.dni = etDni!!.text.toString()
            mCrudListener!!.oBject = user
            tvResultado!!.text = user.name + " " + user.lastName

            mUserRepository.save(user)

            firebaseAnalyticsPresenter.enviarEvento(TagAnalytics.EVENTO_CREAR_USUARIO)
            mActivityListener!!.onNext()
        }
    }

    private fun onTextDniChanged() {
        etDni?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                mUserRepository.getUserByDni(charSequence.toString()).observe(this@NewUserFragment, Observer { user ->
                    if (user != null) {
                        mCrudListener!!.oBject = user
                        etDni!!.setText(user.dni)
                        etNombre!!.setText(user.name)
                        etApellido!!.setText(user.lastName)
                        tvResultado!!.text = user.name + " " + user.lastName
                    }
                })
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
    }

    fun onClickBtnListado() {
        btnListado!!.setOnClickListener { mCrudListener!!.onList() }
    }

    companion object {

        var TAG = NewUserFragment::class.java!!.getName()

        private var mActivityListener: ActionsViewPagerListener? = null
        private var mCrudListener: CrudListener<User>? = null


        // TODO: Rename and change types and number of parameters
        fun newInstance(activityListener: ActionsViewPagerListener?, crudListener: CrudListener<User>): NewUserFragment {
            val fragment = NewUserFragment()
            val args = Bundle()
            fragment.arguments = args
            mActivityListener = activityListener
            mCrudListener = crudListener
            return fragment
        }
    }
}// Required empty public constructor
