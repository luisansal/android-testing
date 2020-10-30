package com.luisansal.jetpack.features.manageusers.listuser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.luisansal.jetpack.R
import com.luisansal.jetpack.features.manageusers.CrudListener
import com.luisansal.jetpack.domain.analytics.TagAnalytics
import com.luisansal.jetpack.domain.entity.User
import com.luisansal.jetpack.features.analytics.FirebaseanalyticsViewModel
import com.luisansal.jetpack.features.analytics.FirebaseanalyticsViewState
import com.luisansal.jetpack.features.manageusers.UserViewState
import com.luisansal.jetpack.features.manageusers.viewmodel.UserViewModel
import com.luisansal.jetpack.utils.injectFragment
import kotlinx.android.synthetic.main.fragment_list_user.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListUserFragment : Fragment(){

    private val userViewModel: UserViewModel by viewModel()
    private val firebaseAnalyticsViewModel: FirebaseanalyticsViewModel by viewModel()
    private val adapterUsuarios: PagedUserAdapter by lazy {
        PagedUserAdapter()
    }

    private fun setupRv() {
        rvUsers.setHasFixedSize(true)
        rvUsers.adapter = adapterUsuarios
        rvUsers.layoutManager = LinearLayoutManager(context)
    }

    private fun onClickBtnNuevoUsuario() {
        btnNuevoUsuario?.setOnClickListener { view -> mCrudListener?.onNew() }
    }

    private fun onClickEliminarUsuarios(){
        userViewModel.deleteUserViewState.observe(::getLifecycle,::observerDeleteDataResponse)
        btnEliminarUsuarios?.setOnClickListener {
            userViewModel.deleteUsers()
        }
    }

    private fun observerDeleteDataResponse(deleteUserViewState: UserViewState) {
        when (deleteUserViewState) {
            is UserViewState.LoadingState -> {
                Log.e(deleteUserViewState.javaClass.name,deleteUserViewState.javaClass.name)
            }
            is UserViewState.DeleteSuccessState -> {
                obtenerUsuarios()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRv()
        onClickBtnNuevoUsuario()
        onClickEliminarUsuarios()
        obtenerUsuarios()

        userViewModel.listUserViewState.observe(::getLifecycle,::observerDataResponse)
        firebaseAnalyticsViewModel.fireBaseAnalyticsViewState.observe(::getLifecycle,::observerEventoMostrarUsuarios)
    }

    private fun obtenerUsuarios(){
        userViewModel.getUsersPaged()
    }

    private fun observerDataResponse(listUserViewState: UserViewState) {
        when (listUserViewState) {
            is UserViewState.LoadingState -> {
                pgbList.visibility = View.VISIBLE
            }
            is UserViewState.ListSuccessPagedState -> {
                listUserViewState.data?.observe(this, Observer {
                    firebaseAnalyticsViewModel.enviarEvento(TagAnalytics.EVENTO_MOSTRAR_USUARIOS)
                    adapterUsuarios.submitList(it)
                    pgbList.visibility = View.GONE
                })
            }
        }
    }

    fun observerEventoMostrarUsuarios(firebaseanalyticsViewState: FirebaseanalyticsViewState){
        when(firebaseanalyticsViewState){
            is FirebaseanalyticsViewState.ErrorState -> {
                Log.e(firebaseanalyticsViewState.javaClass.name,firebaseanalyticsViewState.e.toString())
            }
        }
    }

    companion object {

        var TAG = ListUserFragment::class.java.name
        var mCrudListener: CrudListener<User>? = null

    }
}
