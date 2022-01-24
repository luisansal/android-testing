package com.luisansal.jetpack.features.manageusers.newuser

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import com.luisansal.jetpack.R
import com.luisansal.jetpack.core.base.BaseBindingFragment
import com.luisansal.jetpack.core.domain.entity.User
import com.luisansal.jetpack.core.domain.exceptions.CreateUserValidationException
import com.luisansal.jetpack.core.domain.exceptions.DniValidationException
import com.luisansal.jetpack.core.domain.exceptions.UserExistException
import com.luisansal.jetpack.core.utils.navigationController
import com.luisansal.jetpack.databinding.FragmentNewUserBinding
import com.luisansal.jetpack.features.analytics.FirebaseanalyticsViewModel
import com.luisansal.jetpack.features.analytics.FirebaseanalyticsViewState
import com.luisansal.jetpack.features.manageusers.UserViewState
import com.luisansal.jetpack.features.manageusers.viewmodel.UserViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class NewUserFragment : BaseBindingFragment() {

    private val binding by lazy {
        FragmentNewUserBinding.inflate(layoutInflater).apply { lifecycleOwner = this@NewUserFragment }
    }
    private val userViewModel: UserViewModel by sharedViewModel()
    private val newUserViewModel: NewUserViewModel by viewModel { parametersOf(userViewModel) }
    private val firebaseanalyticsViewModel: FirebaseanalyticsViewModel by viewModel()

    override fun getViewResource() = binding.root

    private val navController: NavController by lazy {
        navigationController(R.id.nav_host_fragment)
    }

    companion object {
        var TAG = NewUserFragment::class.java.name
    }

    private fun notifyUserDeleted() {
        Toast.makeText(context, R.string.user_deleted, Toast.LENGTH_LONG).show()
    }

    private fun notifyUserValidationConstraint() {
        binding.etName.error = "El nombre no debe estar vacío"
        binding.etLastname.error = "El Apellido no debe estar vacío"
    }

    private fun notifyDniUserValidationConstraint() {
        Toast.makeText(context, R.string.dni_ammount_characteres_fail, Toast.LENGTH_LONG).show()
        binding.etDni.error = resources.getString(R.string.dni_ammount_characteres_fail)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = newUserViewModel
        userViewModel.getUser()

        subscribeObservers()
    }

    private fun subscribeObservers() {
        userViewModel.userViewState.observe(viewLifecycleOwner, {
            it ?: return@observe
            when (it) {
                is UserViewState.GetUserSuccessState -> {
                    val user = it.user
                    if (user != null) {
                        notifyUserSaved(user)
                        newUserViewModel.fillFields(it.user)
                    }
                }
                is UserViewState.NewUserSuccess -> {
                    val user = it.user
                    if (user != null) {
                        notifyUserSaved(user)
                    }
                }
            }
        })
        userViewModel.errorDialog.observe(viewLifecycleOwner, {
            when (val e = it) {
                is DniValidationException -> {
                    notifyDniUserValidationConstraint()
                }
                is CreateUserValidationException -> {
                    notifyUserValidationConstraint()
                }
                is UserExistException -> {
                    notifyUserSaved(e.user)
                    nextStep(e.user)
                }
            }
        })
        newUserViewModel.goToListado.observe(viewLifecycleOwner, {
            it ?: return@observe
            if (it)
                navController.navigate(R.id.action_newUserFragment_to_listUserFragment)
        })
        firebaseanalyticsViewModel.fireBaseAnalyticsViewState.observe(::getLifecycle, ::observerFirebaseCrearUsuario)
    }

    private fun nextStep(user: User) {
        UserViewModel.user = user
        navController.navigate(R.id.action_newUserFragment_to_listUserFragment)
    }

    private fun observerFirebaseCrearUsuario(firebaseanalyticsViewState: FirebaseanalyticsViewState) {
        when (firebaseanalyticsViewState) {
            is FirebaseanalyticsViewState.ErrorState -> {
                Log.e(firebaseanalyticsViewState.javaClass.name, firebaseanalyticsViewState.e.toString())
            }
        }
    }

    private fun notifyUserSaved(user: User) {
        Toast.makeText(context, StringBuilder().append(user.names).append(" ").append(user.lastNames).toString(), Toast.LENGTH_LONG).show()
    }

}
