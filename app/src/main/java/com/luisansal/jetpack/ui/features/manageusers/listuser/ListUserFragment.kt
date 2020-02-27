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
        btnNuevoUsuario?.setOnClickListener { view -> mCrudListener!!.onNew() }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.listUserViewState.observe(::getLifecycle,::observerDataResponse)
        userViewModel.getUsersPaged()
        firebaseAnalyticsPresenter.enviarEvento(TagAnalytics.EVENTO_MOSTRAR_USUARIOS)

        onClickBtnNuevoUsuario()
        setupRv()
    }

    private fun observerDataResponse(listUserViewState: ListUserViewState) {

        when (listUserViewState) {
            is ListUserViewState.SuccessPagedState -> {
                listUserViewState.data?.observe(this, Observer {
                    adapterUsuarios.submitList(it)
                })

            }
        }
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
