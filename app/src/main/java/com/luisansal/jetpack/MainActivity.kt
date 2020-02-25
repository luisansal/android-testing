package com.luisansal.jetpack

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.luisansal.jetpack.common.interfaces.ActionsViewPagerListener
import com.luisansal.jetpack.common.interfaces.TitleListener
import com.luisansal.jetpack.ui.MainActivityMVP
import com.luisansal.jetpack.ui.MainActivityPresenter
import com.luisansal.jetpack.common.adapters.MyPagerAdapter
import com.luisansal.jetpack.ui.features.manageusers.ListUserFragment
import com.luisansal.jetpack.ui.features.manageusers.newuser.NewUserFragment
import java.util.*

class MainActivity : AppCompatActivity(), ActionsViewPagerListener, MainActivityMVP.View {

    val PERMISSION_REQUEST_CODE = 4000

    override var fragmentName: String?
        get() {
            return MainActivity.fragmentName
        }
        set(value) {
            MainActivity.fragmentName = value
        }


    private var mViewPager: ViewPager? = null
    private var actionBar: ActionBar? = null
    private var tabListener: ActionBar.TabListener? = null

    override fun setupTabListener() {
        tabListener = object : ActionBar.TabListener {
            override fun onTabSelected(tab: ActionBar.Tab, ft: FragmentTransaction) {
                // show the given tab
                mViewPager!!.currentItem = tab.position
            }

            override fun onTabUnselected(tab: ActionBar.Tab, ft: FragmentTransaction) {
                // hide the given tab
            }

            override fun onTabReselected(tab: ActionBar.Tab, ft: FragmentTransaction) {
                // probably ignore this event
            }
        }
    }

    override fun setupViewPager(fragments: ArrayList<Fragment>) {

        val mDemoCollectionPagerAdapter = MyPagerAdapter(
                supportFragmentManager, fragments)


        mViewPager!!.adapter = mDemoCollectionPagerAdapter
        mViewPager!!.setOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                actionBar!!.setSelectedNavigationItem(position)
            }
        })
    }

    override fun setupActionBar(fragments: ArrayList<Fragment>) {
        actionBar = supportActionBar
        actionBar!!.navigationMode = ActionBar.NAVIGATION_MODE_TABS

        // Add 3 tabs, specifying the tab's text and TabListener
        for (i in fragments.indices) {
            actionBar!!.addTab(
                    actionBar!!.newTab()
                            .setText((fragments[i] as TitleListener).title)
                            .setTabListener(tabListener))
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_CODE)
        }
        setContentView(R.layout.activity_main)
        mViewPager = findViewById(R.id.pager)
        val presenter = MainActivityPresenter(this)
        presenter.init()
    }

    override fun onNext() {
        val position = mViewPager!!.currentItem
        mViewPager!!.currentItem = position + 1
        actionBar!!.setSelectedNavigationItem(position + 1)
    }

    override fun onBackPressed() {
        val fm = supportFragmentManager
        if (fm.backStackEntryCount > 0) {
            Log.i("MainActivity", "popping backstack")
            if (fm.getBackStackEntryAt(fm.backStackEntryCount - 1).name == NewUserFragment.TAG) {
                fragmentName = ListUserFragment.TAG
            }
            if (fm.getBackStackEntryAt(fm.backStackEntryCount - 1).name == ListUserFragment.TAG) {
                fragmentName = NewUserFragment.TAG
            }

            fm.popBackStackImmediate()
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super")
            super.onBackPressed()
        }
    }

    companion object {
        var fragmentName: String? = null
    }
}
