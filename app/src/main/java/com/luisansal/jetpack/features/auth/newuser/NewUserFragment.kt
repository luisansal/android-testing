package com.luisansal.jetpack.features.auth.newuser

import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.luisansal.jetpack.R
import com.luisansal.jetpack.core.base.BaseActivity
import com.luisansal.jetpack.core.base.BaseBindingFragment
import com.luisansal.jetpack.core.dialogs.AlertDialog
import com.luisansal.jetpack.core.utils.snackBar
import com.luisansal.jetpack.databinding.FragmentAuthNewUserBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class NewUserFragment : BaseBindingFragment() {
    private val binding by lazy {
        FragmentAuthNewUserBinding.inflate(layoutInflater).apply { lifecycleOwner = this@NewUserFragment }
    }

    override fun getViewResource() = binding.root

    private val viewModel by viewModel<NewUserViewModel> { parametersOf(requireActivity()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.showLoading.observe(viewLifecycleOwner) {
            it ?: return@observe
            showLoading(it)
        }
        viewModel.errorDialog.observe(viewLifecycleOwner) {
            it ?: return@observe
            var message = ""
            when (it) {
                is FirebaseAuthUserCollisionException -> {
                    message =
                        "La dirección de correo electrónico ya está siendo usada por otro usuario"
                }
                is FirebaseAuthWeakPasswordException -> {
                    message = "La contraseña es inválida, debe tener como mínimo 6 caracteres"
                }
                is FirebaseAuthInvalidCredentialsException -> {
                    message = "Formato incorrecto para el correo electrónico"
                }
            }
            AlertDialog.newInstance(
                title = "Advertencia",
                subtitle = message,
                btnOkText = "Aceptar"
            ).showDialog(childFragmentManager)
        }
        viewModel.userSaved.observe(viewLifecycleOwner) {
            it ?: return@observe
            if (it) {
                requireActivity().onBackPressed()
                (requireActivity() as BaseActivity).hideKeyboard(binding.root)
                requireActivity().snackBar(binding.root,getString(R.string.user_saved))
            }
        }
    }
}