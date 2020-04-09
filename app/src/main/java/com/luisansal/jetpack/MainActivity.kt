package com.luisansal.jetpack

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.luisansal.jetpack.common.adapters.MyPagerAdapter
import com.luisansal.jetpack.common.interfaces.ActionsViewPagerListener
import com.luisansal.jetpack.ui.MainActivityMVP
import com.luisansal.jetpack.ui.MainActivityPresenter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), ActionsViewPagerListener, MainActivityMVP.View {

    val PERMISSION_REQUEST_CODE = 4000

    override var fragmentName: String? = null

    override fun setupTabPager() {
        mainTabs.setupWithViewPager(vwpMain)
    }

    override fun setupViewPager(fragments: ArrayList<Fragment>) {
        vwpMain?.adapter = MyPagerAdapter(supportFragmentManager, fragments)
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
        val presenter = MainActivityPresenter(this)
        presenter.init()
    }

    override fun onNext() {
        val position = vwpMain?.currentItem
        (position?.plus(1))?.let { mainTabs.getTabAt(it)?.select() }
    }

    companion object {
        var fragmentName: String? = null
    }
}
