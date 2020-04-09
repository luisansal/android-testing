package com.luisansal.jetpack.ui.features.manageusers.newuser

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.luisansal.jetpack.R
import com.luisansal.jetpack.common.interfaces.ActionsViewPagerListener
import com.luisansal.jetpack.ui.features.manageusers.CrudListener
import com.luisansal.jetpack.domain.entity.User
import com.luisansal.jetpack.domain.analytics.TagAnalytics
import com.luisansal.jetpack.domain.exception.DniValidationException
import com.luisansal.jetpack.ui.features.analytics.FirebaseAnalyticsPresenter
import com.luisansal.jetpack.ui.features.manageusers.RoomViewModel
import com.luisansal.jetpack.ui.utils.injectFragment
import kotlinx.android.synthetic.main.fragment_new_user.*
import java.lang.Exception
import java.lang.StringBuilder

class NewUserFragment : Fragment(), NewUserMVP.View {
    override fun resetView() {
        etDni.setText("")
        etNombre.setText("")
        etApellido.setText("")
    }

    override fun notifyUserDeleted() {
        Toast.makeText(context, R.string.user_deleted, Toast.LENGTH_LONG).show()
    }

    override fun onClickBtnEliminar() {
        btnEliminar.setOnClickListener{
            newUserPresenter.deleteUser(etDni.text.toString())
        }
    }

    override fun notifyUserValidationConstraint() {
        Toast.makeText(context, R.string.dni_ammount_characteres_fail, Toast.LENGTH_LONG).show()
    }

    private val newUserPresenter: NewUserPresenter by injectFragment()

    private val firebaseAnalyticsPresenter: FirebaseAnalyticsPresenter by injectFragment()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = mViewModel?.user

        if (user != null) {
            etDni.setText(user.dni)
            printUser(user)
        }
        onClickBtnSiguiente()
        onClickBtnListado()
        onTextDniChanged()
        onClickBtnEliminar()
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

    override fun printUser(user: User) {
        mViewModel?.user = user
        etNombre?.setText(user.name)
        etApellido?.setText(user.lastName)
        tvResultado?.text = StringBuilder().append(user.name).append(" ").append(user.lastName)
    }

    override fun onClickBtnSiguiente() {
        btnSiguiente?.setOnClickListener {
            val user = User()
            user.name = etNombre!!.text.toString()
            user.lastName = etApellido!!.text.toString()
            user.dni = etDni!!.text.toString()
            tvResultado!!.text = StringBuilder().append(user.name).append(" ").append(user.lastName)

            try {
                newUserPresenter.newUser(user)
            } catch (e : Exception){
                when(e){
                    is DniValidationException -> {
                        notifyUserValidationConstraint()
                        return@setOnClickListener
                    }
                }
            }

            firebaseAnalyticsPresenter.enviarEvento(TagAnalytics.EVENTO_CREAR_USUARIO)
            mActivityListener!!.onNext()
        }
    }

    override fun notifyUserSaved(name: String) {
        Toast.makeText(context, name, Toast.LENGTH_LONG).show()
    }

    override fun onTextDniChanged() {
        etDni?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                newUserPresenter.getUser(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
    }

    override fun onClickBtnListado() {
        btnListado?.setOnClickListener { mCrudListener!!.onList() }
    }

    companion object {

        var TAG = NewUserFragment::class.java.name
        var mActivityListener: ActionsViewPagerListener? = null
        var mCrudListener: CrudListener<User>? = null
        var mViewModel: RoomViewModel? = null

        fun newInstance(crudListener: CrudListener<User>, viewModel: RoomViewModel): NewUserFragment {
            val fragment = NewUserFragment()
            val args = Bundle()
            fragment.arguments = args
            mCrudListener = crudListener
            mViewModel = viewModel
            return fragment
        }
    }
}
