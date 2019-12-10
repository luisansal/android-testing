package com.luisansal.jetpack.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
class MyPagerAdapter(fm: FragmentManager, internal var mFragments: List<Fragment>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(i: Int): Fragment {
        //        Fragment fragment = new DemoObjectFragment();
        //        Bundle args = new Bundle();
        //        // Our object is just an integer :-P
        //        args.putInt(DemoObjectFragment.ARG_OBJECT, i + 1);
        //        fragment.setArguments(args);
        return mFragments[i]
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "OBJECT " + (position + 1)
    }
}
