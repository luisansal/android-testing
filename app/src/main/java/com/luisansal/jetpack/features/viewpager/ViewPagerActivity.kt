package com.luisansal.jetpack.features.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.luisansal.jetpack.core.base.BaseBindingActivity
import com.luisansal.jetpack.core.utils.enableTouch
import com.luisansal.jetpack.core.utils.injectActivity
import com.luisansal.jetpack.databinding.ActivityViewpagerBinding
import java.util.*

class ViewPagerActivity : BaseBindingActivity(), ViewPagerMVP.View, ActionsViewPagerListener {

    private val presenter by injectActivity<ViewPagerPresenter>()
    val binding by lazy {
        ActivityViewpagerBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@ViewPagerActivity
        }
    }
    override fun getViewResource() = binding.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.init(null)
    }

    override var fragmentName: String? = null
    override fun setupTabPager() {
        binding.mainTabs.setupWithViewPager(binding.vwpMain)
        binding.mainTabs.tabMode = TabLayout.MODE_SCROLLABLE
    }

    override fun setupViewPager(fragments: ArrayList<Fragment>) {
        binding.vwpMain?.adapter = MyPagerAdapter(supportFragmentManager, fragments)
    }

    override fun onNext() {

        val position = binding.vwpMain?.currentItem?.plus(1)

        position?.let {
            goTo(it)
        }
    }

    override fun goTo(index: Int) {
        binding.mainTabs.getTabAt(index)?.enableTouch()
        binding.mainTabs.getTabAt(index)?.select()
    }
}