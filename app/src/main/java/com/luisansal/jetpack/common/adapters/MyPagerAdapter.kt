package com.luisansal.jetpack.common.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.luisansal.jetpack.common.interfaces.TitleListener

class MyPagerAdapter(fm: FragmentManager, private val mFragments: List<Fragment>) :
        FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getItem(position: Int): Fragment {

        return mFragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return (mFragments[position] as TitleListener).title
    }
}
