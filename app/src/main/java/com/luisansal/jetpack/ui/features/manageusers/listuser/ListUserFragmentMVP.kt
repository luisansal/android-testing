package com.luisansal.jetpack.ui.features.manageusers.listuser

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.luisansal.jetpack.domain.entity.User
import com.luisansal.jetpack.ui.features.manageusers.RoomViewModel

interface ListUserFragmentMVP {

    interface View : LifecycleOwner {
        fun setupRoomViewModel()
        fun setupRv(pagedAdapter: PagedUserAdapter)
        fun onClickBtnNuevoUsuario()
        val roomViewModel : RoomViewModel
        fun validarRvUsuariosPopulado()
        fun rvUsuariosPopulado()
        fun rvUsuariosNoPopulado()
    }

    interface Presenter{
        fun init()
        fun setupRoomViewModel()
        fun setupRv()
        fun populateAdapterRv(users: LiveData<PagedList<User>>)
        fun populateRoomViewModel(users: LiveData<PagedList<User>>)
        fun validarCantidadPaginacion(numeroComparar : Int)
        fun cantidadValida()
        fun cantidadInvalida()
        fun cantidadUsuarios(): Int
        fun validarRvUsuariosPopulado()
        fun rvUsuariosPopulado()
        fun rvUsuariosNoPopulado()
    }

    interface Interactor{
        fun setupLivePaged()
        fun validarCantidadPaginacion(numeroComparar : Int)
        fun validarRvUsuariosPopulado()
    }
}