package com.luisansal.jetpack.features.manageusers.listuser

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import com.luisansal.jetpack.R
import com.luisansal.jetpack.core.base.BaseBindingFragment
import com.luisansal.jetpack.core.utils.navigationController
import com.luisansal.jetpack.databinding.FragmentListUserBinding
import com.luisansal.jetpack.domain.analytics.TagAnalytics
import com.luisansal.jetpack.features.analytics.FirebaseanalyticsViewModel
import com.luisansal.jetpack.features.analytics.FirebaseanalyticsViewState
import com.luisansal.jetpack.features.manageusers.UserViewState
import com.luisansal.jetpack.features.manageusers.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListUserFragment : BaseBindingFragment() {
    private val binding by lazy {
        FragmentListUserBinding.inflate(layoutInflater).apply { lifecycleOwner = this@ListUserFragment }
    }

    override fun getViewResource() = binding.root

    override fun getViewIdResource() = R.layout.fragment_list_user
    private val userViewModel by viewModel<UserViewModel>()
    private val firebaseAnalyticsViewModel: FirebaseanalyticsViewModel by viewModel()
    private val viewModel by viewModel<ListUserViewModel>()

    private fun onClickBtnNuevoUsuario() {
        binding.btnNuevoUsuario?.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun onClickEliminarUsuarios() {
        userViewModel.deleteUserViewState.observe(::getLifecycle, ::observerDeleteDataResponse)
        binding.btnEliminarUsuarios?.setOnClickListener {
            userViewModel.deleteUsers()
        }
    }

    private fun observerDeleteDataResponse(deleteUserViewState: UserViewState) {
        when (deleteUserViewState) {
            is UserViewState.LoadingState -> {
                Log.e(deleteUserViewState.javaClass.name, deleteUserViewState.javaClass.name)
            }
            is UserViewState.DeleteSuccessState -> {
                obtenerUsuarios()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        subscribeObservers()
        onClickBtnNuevoUsuario()
        onClickEliminarUsuarios()
        obtenerUsuarios()

    }

    private fun obtenerUsuarios() {
        userViewModel.getUsersPaged()
    }

    private fun subscribeObservers() {
        userViewModel.listUserViewState.observe(::getLifecycle, ::observerDataResponse)
        firebaseAnalyticsViewModel.fireBaseAnalyticsViewState.observe(::getLifecycle, ::observerEventoMostrarUsuarios)
        viewModel.name.observe(viewLifecycleOwner, {
            it ?: return@observe
            if (it.isNotEmpty()) userViewModel.getByNamesPaged(it) else userViewModel.getUsersPaged()
        })
    }

    private fun observerDataResponse(listUserViewState: UserViewState) {
        when (listUserViewState) {
            is UserViewState.LoadingState -> {
                showLoading(listUserViewState.isLoading)
            }
            is UserViewState.ListSuccessPagedState -> {
                listUserViewState.data?.observe(this, {
                    it ?: return@observe
                    firebaseAnalyticsViewModel.enviarEvento(TagAnalytics.EVENTO_MOSTRAR_USUARIOS)
                    viewModel.adapterUsuarios.submitList(it){
                        binding.rvUsers.scrollToPosition(0)
                    }
                })
            }
        }
    }

    private fun observerEventoMostrarUsuarios(firebaseanalyticsViewState: FirebaseanalyticsViewState) {
        when (firebaseanalyticsViewState) {
            is FirebaseanalyticsViewState.ErrorState -> {
                Log.e(firebaseanalyticsViewState.javaClass.name, firebaseanalyticsViewState.e.toString())
            }
        }
    }
}
