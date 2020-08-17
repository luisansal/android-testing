package com.luisansal.jetpack.common.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.luisansal.jetpack.common.interfaces.TitleListener

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
class MyPagerAdapter(fm: FragmentManager,private val mFragments: List<Fragment>, private val lifecycle : Lifecycle) : FragmentStateAdapter(fm,lifecycle) {
    override fun getItemCount(): Int {
        return mFragments.size
    }

    override fun createFragment(position: Int): Fragment {

        return mFragments[position]
    }

    fun getPageTitle(position: Int): CharSequence?  {
        return (mFragments[position] as TitleListener).title
    }
}
