package com.luisansal.jetpack.ui.features.manageusers.listuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.luisansal.jetpack.R
import com.luisansal.jetpack.common.interfaces.CrudListener
import com.luisansal.jetpack.domain.analytics.TagAnalytics
import com.luisansal.jetpack.ui.features.analytics.FirebaseAnalyticsPresenter
import com.luisansal.jetpack.ui.features.manageusers.RoomViewModel
import com.luisansal.jetpack.ui.utils.injectFragment
import kotlinx.android.synthetic.main.fragment_list_user.*
import org.koin.android.ext.android.inject

class ListUserFragment : Fragment(), ListUserFragmentMVP.View {

    private val mPresenter: ListUserFragmentPresenter by injectFragment()

    private val firebaseAnalyticsPresenter: FirebaseAnalyticsPresenter by injectFragment()

    override fun validarRvUsuariosPopulado() {
        mPresenter.validarRvUsuariosPopulado()
    }

    override fun rvUsuariosPopulado() {

    }

    override fun rvUsuariosNoPopulado() {

    }

    override val roomViewModel: RoomViewModel
        get() {
            return mRoomViewModel
        }

    private lateinit var mRoomViewModel: RoomViewModel

    override fun setupRv(pagedUserAdapter: PagedUserAdapter) {
        rvUsers.setHasFixedSize(true)
        rvUsers.adapter = pagedUserAdapter
        rvUsers.layoutManager = LinearLayoutManager(context)
    }


    override fun setupRoomViewModel() {
        mRoomViewModel = ViewModelProviders.of(this).get(RoomViewModel::class.java)
    }

    override fun onClickBtnNuevoUsuario() {
        btnNuevoUsuario?.setOnClickListener { view -> mCrudListener!!.onNew() }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_user, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPresenter.init()
        firebaseAnalyticsPresenter.enviarEvento(TagAnalytics.EVENTO_MOSTRAR_USUARIOS)
    }

    companion object {

        var TAG = ListUserFragment::class.java.name
        private var mCrudListener: CrudListener<*>? = null

        // TODO: Rename and change types and number of parameters
        fun newInstance(crudListener: CrudListener<*>): ListUserFragment {
            val fragment = ListUserFragment()
            val args = Bundle()
            fragment.arguments = args
            mCrudListener = crudListener
            return fragment
        }
    }
}
