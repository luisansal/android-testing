package com.luisansal.jetpack.ui.features.manageusers.mvp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.luisansal.jetpack.domain.entity.User
import com.luisansal.jetpack.ui.features.manageusers.PagedUserAdapter

class ListUserFragmentPresenter(private val context: Context, private val mView: ListUserFragmentMVP.View) : ListUserFragmentMVP.Presenter {

    val adapterUsuarios: PagedUserAdapter? by lazy {
        PagedUserAdapter()
    }

    private var mNumUsers: Int = 0

    private val mInteractor: ListUserFragmentMVP.Interactor = ListUserFragmentInteractor(context, this)

    override fun validarRvUsuariosPopulado() {
        mInteractor.validarRvUsuariosPopulado()
    }

    override fun rvUsuariosPopulado() {
        mView.rvUsuariosPopulado()
    }

    override fun rvUsuariosNoPopulado() {
        mView.rvUsuariosNoPopulado()
    }

    override fun populateAdapterRv(users: LiveData<PagedList<User>>) {

//        mView.roomViewModel.getUsers().observe(this, adqapter::submitList);
        mView.roomViewModel.users.observe(mView, Observer<PagedList<User>> { adapterUsuarios?.submitList(it) })
    }

    override fun cantidadValida() {

    }

    override fun cantidadInvalida() {

    }

    override fun cantidadUsuarios(): Int {
        return adapterUsuarios?.itemCount ?: 0
    }

    override fun validarCantidadPaginacion(numeroComparar: Int) {
        mInteractor.validarCantidadPaginacion(numeroComparar)
    }

    override fun init() {
        setupRoomViewModel()
        setupRv()
        mInteractor.setupLivePaged()
        mView.onClickBtnNuevoUsuario()
    }

    override fun setupRoomViewModel() {
        mView.setupRoomViewModel()
    }

    override fun setupRv() {
        adapterUsuarios?.let { mView.setupRv(it) }
    }

    override fun populateRoomViewModel(users: LiveData<PagedList<User>>) {
        mView.roomViewModel.users = users
    }
}