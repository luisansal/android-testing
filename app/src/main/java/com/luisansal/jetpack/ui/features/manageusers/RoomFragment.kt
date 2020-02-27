package com.luisansal.jetpack.ui.features.manageusers

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.luisansal.jetpack.R
import com.luisansal.jetpack.common.interfaces.ActionsViewPagerListener
import com.luisansal.jetpack.common.interfaces.TitleListener
import com.luisansal.jetpack.domain.entity.User
import com.luisansal.jetpack.ui.features.manageusers.listuser.ListUserFragment
import com.luisansal.jetpack.ui.features.manageusers.newuser.NewUserFragment

class RoomFragment : Fragment(), TitleListener, CrudListener<User>, RoomFragmentMVP.View {

    override val title = "Room Manager"
    private lateinit var mViewModel: RoomViewModel
    private var mActionsViewPagerListener: ActionsViewPagerListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.room_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel = ViewModelProviders.of(this).get(RoomViewModel::class.java)

        // Begin the transaction
        mActionsViewPagerListener = activity as ActionsViewPagerListener

        val presenter = RoomFragmentPresenter(this)
        presenter.init()
    }

    override fun onList() {
        val fm = activity!!.supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(R.id.parent_fragment_container, ListUserFragment.newInstance(this), ListUserFragment.TAG)
                .addToBackStack(ListUserFragment.TAG)
                .commit()
        mActionsViewPagerListener?.fragmentName = ListUserFragment.TAG
    }

    override fun onNew() {
        val ft = activity!!.supportFragmentManager.beginTransaction()
        ft.replace(R.id.parent_fragment_container, NewUserFragment.newInstance(activity as ActionsViewPagerListener?, this, mViewModel), NewUserFragment.TAG)
                .addToBackStack(NewUserFragment.TAG)
                .commit()
        mActionsViewPagerListener?.fragmentName = NewUserFragment.TAG
    }

    override fun onEdit() {
        //TODO()
    }

    override fun setOBjects(oBjects: List<User>) {
        //TODO()
    }

    override fun switchNavigation() {
        val ft = activity!!.supportFragmentManager.beginTransaction()
        ft.replace(R.id.parent_fragment_container, NewUserFragment.newInstance(mActionsViewPagerListener, this, mViewModel), NewUserFragment.TAG)
                .commit()

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
