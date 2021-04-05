package com.luisansal.jetpack.features.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.luisansal.jetpack.R
import com.luisansal.jetpack.base.BaseActivity
import com.luisansal.jetpack.utils.enableTouch
import com.luisansal.jetpack.utils.injectActivity
import kotlinx.android.synthetic.main.activity_viewpager.*
import java.util.*

class ViewPagerActivity : BaseActivity(), ViewPagerMVP.View, ActionsViewPagerListener {
    override fun getViewIdResource() = R.layout.activity_viewpager

    private val presenter by injectActivity<ViewPagerPresenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.init(null)
    }

    override var fragmentName: String? = null
    override fun setupTabPager() {
        mainTabs.setupWithViewPager(vwpMain)
        mainTabs.tabMode = TabLayout.MODE_SCROLLABLE
    }

    override fun setupViewPager(fragments: ArrayList<Fragment>) {
        vwpMain?.adapter = MyPagerAdapter(supportFragmentManager, fragments)
    }

    override fun onNext() {

        val position = vwpMain?.currentItem?.plus(1)

        position?.let {
            goTo(it)
        }
    }

    override fun goTo(index: Int) {
        mainTabs.getTabAt(index)?.enableTouch()
        mainTabs.getTabAt(index)?.select()
    }
}