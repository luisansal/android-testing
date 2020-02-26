package com.luisansal.jetpack.ui.features.manageusers.listuser

import android.content.Context
import androidx.paging.LivePagedListBuilder
import com.luisansal.jetpack.data.repository.UserRepository

class ListUserFragmentInteractor(private val context: Context, val presenter: ListUserFragmentMVP.Presenter) : ListUserFragmentMVP.Interactor {

    override fun validarRvUsuariosPopulado() {
        if (presenter.cantidadUsuarios() > 0) {
            presenter.rvUsuariosPopulado()
        } else {
            presenter.rvUsuariosNoPopulado()
        }
    }

    override fun validarCantidadPaginacion(numeroComparar: Int) {
        if (numeroComparar == presenter.cantidadUsuarios()) {
            presenter.cantidadValida()
        } else {
            presenter.cantidadInvalida()
        }
    }

    private var mNumUsers: Int = 0

    override fun setupLivePaged() {
        //        PagedList.Config config = new PagedList.Config.Builder()
        //                .setPageSize(100)
        //                .setInitialLoadSizeHint(100)
        //                .setPrefetchDistance(100)
        //                .setEnablePlaceholders(true)
        //                .build();
        val users = LivePagedListBuilder(UserRepository.newInstance(context).allUsersPaging, 50).build()

        presenter.populateRoomViewModel(users)
        presenter.populateAdapterRv(users)

    }

}