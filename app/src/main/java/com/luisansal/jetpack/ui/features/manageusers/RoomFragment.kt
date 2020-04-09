package com.luisansal.jetpack.ui.features.manageusers

import android.content.Context
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
    private val mViewModel: RoomViewModel by lazy {
        ViewModelProviders.of(this).get(RoomViewModel::class.java)
    }
    private var mActionsViewPagerListener: ActionsViewPagerListener? = null
    private val navController: NavController by lazy {
        getFragmentNavController(R.id.nav_host_fragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.room_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // This callback will only be called when MyFragment is at least Started.
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                // Handle the back button event
                if (!navController.popBackStack()) {
                    // Call finish() on your Activity
                    requireActivity().finish()
                }

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

        val presenter = RoomFragmentPresenter(this)
        presenter.init()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ActionsViewPagerListener) {
            mActionsViewPagerListener = context
        } else {
            throw RuntimeException(context.toString()
                    + " must implement " + NewUserFragment.mActivityListener!!.javaClass.getSimpleName())
        }
    }

    override fun onList() {
        ListUserFragment.mCrudListener = this
        navController.navigate(R.id.action_newUserFragment_to_listUserFragment)
        mActionsViewPagerListener?.fragmentName = ListUserFragment.TAG
    }

    override fun onNew() {
        NewUserFragment.mCrudListener = this
        NewUserFragment.mViewModel = mViewModel
        navController.navigate(R.id.action_listUserFragment_to_newUserFragment)
        mActionsViewPagerListener?.fragmentName = NewUserFragment.TAG
    }

    override fun onEdit() {
        //TODO()
    }

    override fun setOBjects(oBjects: List<User>) {
        //TODO()
    }

    override fun switchNavigation() {
        NewUserFragment.mCrudListener = this
        NewUserFragment.mViewModel = mViewModel

        if (tagFragment != null) {
            if (tagFragment == NewUserFragment.TAG) {
                onNew()
            } else if (tagFragment == ListUserFragment.TAG) {
                onList()
            }
        }
    }

    override var tagFragment: String? = mActionsViewPagerListener?.fragmentName

    companion object {

        fun newInstance(): RoomFragment {
            return RoomFragment()
        }
    }
}
