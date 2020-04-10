package com.luisansal.jetpack.ui.features.manageusers.listuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.luisansal.jetpack.R
import com.luisansal.jetpack.ui.features.manageusers.CrudListener
import com.luisansal.jetpack.domain.analytics.TagAnalytics
import com.luisansal.jetpack.domain.entity.User
import com.luisansal.jetpack.ui.features.analytics.FirebaseAnalyticsPresenter
import com.luisansal.jetpack.ui.features.manageusers.viewmodel.UserViewModel
import com.luisansal.jetpack.ui.utils.injectFragment
import kotlinx.android.synthetic.main.fragment_list_user.*

class ListUserFragment : Fragment(){

    private val userViewModel: UserViewModel by injectFragment()
    private val firebaseAnalyticsPresenter: FirebaseAnalyticsPresenter by injectFragment()
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

    private fun observerDeleteDataResponse(deleteUserViewState: DeleteUserViewState) {
        when (deleteUserViewState) {
            is DeleteUserViewState.SuccessState -> {
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
    }

    private fun obtenerUsuarios(){
        userViewModel.getUsersPaged()
        userViewModel.listUserViewState.observe(::getLifecycle,::observerDataResponse)
    }

    private fun observerDataResponse(listUserViewState: ListUserViewState) {

        when (listUserViewState) {
            is ListUserViewState.LoadingState -> {
                pgbList.visibility = View.VISIBLE
            }
            is ListUserViewState.SuccessPagedState -> {
                listUserViewState.data?.observe(this, Observer {
                    firebaseAnalyticsPresenter.enviarEvento(TagAnalytics.EVENTO_MOSTRAR_USUARIOS)
                    adapterUsuarios.submitList(it)
                    pgbList.visibility = View.GONE
                })
            }
        }
    }

    companion object {

        var TAG = ListUserFragment::class.java.name
        var mCrudListener: CrudListener<User>? = null

    }
}
