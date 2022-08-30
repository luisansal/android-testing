package com.luisansal.jetpack.features.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import com.luisansal.jetpack.R
import com.luisansal.jetpack.core.base.BaseBindingFragment
import com.luisansal.jetpack.core.utils.afterTextChanged
import com.luisansal.jetpack.core.utils.navigationController
import com.luisansal.jetpack.core.utils.snackBar
import com.luisansal.jetpack.databinding.FragmentLoginBinding
import com.luisansal.jetpack.features.auth.LoggedInUserView
import com.luisansal.jetpack.features.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class LoginFragment : BaseBindingFragment() {
    private val binding by lazy {
        FragmentLoginBinding.inflate(layoutInflater).apply { lifecycleOwner = this@LoginFragment }
    }

    override fun getViewResource() = binding.root
    private val viewModel by viewModel<LoginViewModelFirebase> { parametersOf(requireActivity()) }
    private val navigationController by lazy {
        navigationController(R.id.login_nav_host_fragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        subscribeObservers()

        binding.etUsername.afterTextChangeListener {
            viewModel.loginDataChanged(
                binding.etUsername.text.toString(),
                binding.etPassword.text.toString()
            )
        }

        binding.etPassword.apply {
            afterTextChanged {
                viewModel.loginDataChanged(
                    binding.etUsername.text.toString(),
                    binding.etPassword.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        viewModel.login(
                            binding.etUsername.text.toString(),
                            binding.etPassword.text.toString()
                        )
                }
                false
            }

            binding.btnLogin.setOnClickListener {
                viewModel.login(binding.etUsername.text.toString(), binding.etPassword.text.toString())
            }
        }

    }

    private fun subscribeObservers() {
        viewModel.goToNewUser.observe(viewLifecycleOwner) {
            it ?: return@observe
            if (it) {
                navigationController.navigate(LoginFragmentDirections.actionFormLoginToNewUser())
            }
        }

        viewModel.loginFormState.observe(viewLifecycleOwner) {
            val loginState = it ?: return@observe

            // disable login button unless both username / password is valid
            binding.btnLogin.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                binding.etUsername.validated(false, getString(loginState.usernameError))
            } else {
                binding.etUsername.validated = true
            }
            if (loginState.passwordError != null) {
                binding.etPassword.error = getString(loginState.passwordError)
            }
        }

        viewModel.loginResult.observe(viewLifecycleOwner) {
            val loginResult = it ?: return@observe

            if (loginResult.error != null) {
                showMessage(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
                //Complete and destroy login activity once successful
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            }
        }

        viewModel.showLoading.observe(viewLifecycleOwner) {
            it ?: return@observe
            showLoading(it)
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        requireActivity().snackBar(binding.root, "$welcome $displayName")
    }
}