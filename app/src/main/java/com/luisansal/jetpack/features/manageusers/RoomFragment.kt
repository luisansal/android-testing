package com.luisansal.jetpack.features.manageusers

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.luisansal.jetpack.R
import com.luisansal.jetpack.common.interfaces.ActionsViewPagerListener
import com.luisansal.jetpack.common.interfaces.TitleListener
import com.luisansal.jetpack.core.domain.entity.User
import com.luisansal.jetpack.core.utils.navigationController

class RoomFragment : Fragment(), TitleListener, CrudListener<User>, RoomFragmentMVP.View {

    override val title = "Room Manager"
    private var mActionsViewPagerListener: ActionsViewPagerListener? = null
    private val navController: NavController by lazy {
        navigationController(R.id.nav_host_fragment)
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
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        val presenter = RoomFragmentPresenter(this)
        presenter.init()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ActionsViewPagerListener) {
            mActionsViewPagerListener = context
        } else {
            throw RuntimeException(context.toString()
                    + " must implement " + mActionsViewPagerListener?.javaClass?.getSimpleName())
        }
    }

    override fun onList() {
        navController.navigate(R.id.action_newUserFragment_to_listUserFragment)
    }

    override fun onNew() = Unit

    override fun onEdit() {
        //TODO()
    }

    override fun setOBjects(oBjects: List<User>) {
        //TODO()
    }

    override fun switchNavigation() {

//        if (tagFragment != null) {
//            if (tagFragment == NewUserFragment.TAG) {
//                onNew()
//            } else if (tagFragment == ListUserFragment.TAG) {
//                onList()
//            }
//        }
    }

    override var tagFragment: String? = mActionsViewPagerListener?.fragmentName

    companion object {

        fun newInstance(): RoomFragment {
            return RoomFragment()
        }
    }
}
