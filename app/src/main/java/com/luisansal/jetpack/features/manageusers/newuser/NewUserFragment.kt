package com.luisansal.jetpack.features.manageusers.newuser

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import com.luisansal.jetpack.R
import com.luisansal.jetpack.core.base.BaseFragment
import com.luisansal.jetpack.core.domain.entity.User
import com.luisansal.jetpack.domain.analytics.TagAnalytics
import com.luisansal.jetpack.core.domain.exceptions.CreateUserValidationException
import com.luisansal.jetpack.core.domain.exceptions.DniValidationException
import com.luisansal.jetpack.core.domain.exceptions.UserExistException
import com.luisansal.jetpack.features.analytics.FirebaseanalyticsViewModel
import com.luisansal.jetpack.features.analytics.FirebaseanalyticsViewState
import com.luisansal.jetpack.features.login.LoginActivity
import com.luisansal.jetpack.features.manageusers.UserViewState
import com.luisansal.jetpack.features.manageusers.viewmodel.UserViewModel
import com.luisansal.jetpack.core.utils.getFragmentNavController
import com.luisansal.jetpack.core.utils.injectFragment
import kotlinx.android.synthetic.main.fragment_new_user.*
import java.lang.Exception
import java.lang.StringBuilder

class NewUserFragment : BaseFragment(), NewUserMVP.View {

    private val mViewModel: UserViewModel by injectFragment()

    override fun getViewIdResource() = R.layout.fragment_new_user
    private val navController: NavController by lazy {
        getFragmentNavController(R.id.nav_host_fragment)
    }
    companion object {
        var TAG = NewUserFragment::class.java.name
    }

    override fun resetView() {
        etDni.setText("")
        etNombre.setText("")
        etApellido.setText("")
    }

    override fun notifyUserDeleted() {
        Toast.makeText(context, R.string.user_deleted, Toast.LENGTH_LONG).show()
    }

    override fun notifyUserValidationConstraint() {
        etNombre.error = "El nombre no debe estar vacío"
        etApellido.error = "El Apellido no debe estar vacío"
    }

    override fun notifyDniUserValidationConstraint() {
        Toast.makeText(context, R.string.dni_ammount_characteres_fail, Toast.LENGTH_LONG).show()
        etDni.error = resources.getString(R.string.dni_ammount_characteres_fail)
    }

    private val newUserPresenter: NewUserPresenter by injectFragment()
    private val firebaseanalyticsViewModel: FirebaseanalyticsViewModel by injectFragment()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.userViewState.observe(::getLifecycle, ::observerUser)
        mViewModel.getUser()

        onClickBtnSiguiente()
        onClickBtnListado()
    }

    fun observerUser(userViewState: UserViewState) {
        when (userViewState) {
            is UserViewState.CrearGetSuccessState -> {
                val user = userViewState.user
                if (user != null) {
                    printUser(user)
                }
            }
        }
    }

    override fun printUser(user: User) {
        etNombre?.setText(user.names)
        etApellido?.setText(user.lastNames)
        tvResultado?.text = StringBuilder().append(user.names).append(" ").append(user.lastNames)
        notifyUserSaved(user)
    }

    override fun nextStep(user: User) {
        UserViewModel.user = user
        navController.navigate(R.id.action_newUserFragment_to_listUserFragment)
    }

    override fun onClickBtnSiguiente() {
        firebaseanalyticsViewModel.fireBaseAnalyticsViewState.observe(::getLifecycle, ::observerFirebaseCrearUsuario)
        btnSiguiente?.setOnClickListener {
            val user = User()
            user.names = etNombre?.text.toString()
            user.lastNames = etApellido?.text.toString()
            user.dni = etDni?.text.toString()
            tvResultado?.text = StringBuilder().append(user.names).append(" ").append(user.lastNames)

            try {
                newUserPresenter.newUser(user)
            } catch (e: Exception) {
                when (e) {
                    is DniValidationException -> {
                        notifyDniUserValidationConstraint()
                    }
                    is CreateUserValidationException -> {
                        notifyUserValidationConstraint()
                    }
                    is UserExistException -> {
                        printUser(e.user)
                        nextStep(e.user)
                    }
                }
            }

            firebaseanalyticsViewModel.enviarEvento(TagAnalytics.EVENTO_CREAR_USUARIO)
        }
    }

    private fun observerFirebaseCrearUsuario(firebaseanalyticsViewState: FirebaseanalyticsViewState) {
        when (firebaseanalyticsViewState) {
            is FirebaseanalyticsViewState.ErrorState -> {
                Log.e(firebaseanalyticsViewState.javaClass.name, firebaseanalyticsViewState.e.toString())
            }
        }
    }

    override fun notifyUserSaved(user: User) {
        Toast.makeText(context, StringBuilder().append(user.names).append(" ").append(user.lastNames).toString(), Toast.LENGTH_LONG).show()
    }

    override fun onClickBtnListado() {
        btnListado?.setOnClickListener {
            navController.navigate(R.id.action_newUserFragment_to_listUserFragment)
        }
    }

    override fun afterLogout(){
        startActivity(Intent(requireContext(), LoginActivity::class.java))
    }
}
