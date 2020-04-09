package com.luisansal.jetpack.ui.features.manageusers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import com.luisansal.jetpack.R
import com.luisansal.jetpack.common.interfaces.ActionsViewPagerListener
import com.luisansal.jetpack.common.interfaces.TitleListener
import com.luisansal.jetpack.domain.entity.User
import com.luisansal.jetpack.ui.features.manageusers.listuser.ListUserFragment
import com.luisansal.jetpack.ui.features.manageusers.newuser.NewUserFragment
import com.luisansal.jetpack.ui.utils.getFragmentNavController


class RoomFragment : Fragment(), TitleListener, CrudListener<User>, RoomFragmentMVP.View {

    override val title = "Room Manager"
    private lateinit var mViewModel: RoomViewModel
    private var mActionsViewPagerListener: ActionsViewPagerListener? = null
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.room_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = getFragmentNavController(R.id.nav_host_fragment)
        mViewModel = ViewModelProviders.of(this).get(RoomViewModel::class.java)
        mActionsViewPagerListener = activity as ActionsViewPagerListener

        // This callback will only be called when MyFragment is at least Started.
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                // Handle the back button event
                navController.popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

        val presenter = RoomFragmentPresenter(this)
        presenter.init()
    }

    override fun onList() {
        ListUserFragment.newInstance(this)
        navController.navigate(R.id.listUserFragment)
        mActionsViewPagerListener?.fragmentName = ListUserFragment.TAG
    }

    override fun onNew() {
        NewUserFragment.newInstance(this, mViewModel)
        navController.navigate(R.id.newUserFragment)
        mActionsViewPagerListener?.fragmentName = NewUserFragment.TAG
    }

    override fun onEdit() {
        //TODO()
    }

    override fun setOBjects(oBjects: List<User>) {
        //TODO()
    }

    override fun switchNavigation() {
        NewUserFragment.newInstance(this, mViewModel)
        navController.navigate(R.id.newUserFragment)

        if (getTagFragment() != null) {
            if (getTagFragment() == NewUserFragment.TAG) {
                onNew()
            } else if (getTagFragment() == ListUserFragment.TAG) {
                onList()
            }
        }
    }

    override fun getTagFragment(): String? {
        return mActionsViewPagerListener?.fragmentName
    }

    companion object {

        fun newInstance(): RoomFragment {
            return RoomFragment()
        }
    }
}
