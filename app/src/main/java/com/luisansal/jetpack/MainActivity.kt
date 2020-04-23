package com.luisansal.jetpack

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.luisansal.jetpack.common.adapters.MyPagerAdapter
import com.luisansal.jetpack.common.interfaces.ActionsViewPagerListener
import com.luisansal.jetpack.ui.MainActivityMVP
import com.luisansal.jetpack.ui.MainActivityPresenter
import com.luisansal.jetpack.ui.PopulateViewModel
import com.luisansal.jetpack.ui.PopulateViewState
import com.luisansal.jetpack.ui.utils.enableTouch
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import java.util.*


class MainActivity : AppCompatActivity(), ActionsViewPagerListener, MainActivityMVP.View {

    companion object{
        val PERMISSION_REQUEST_CODE = 4000
        val POSITION = "position"
    }


    override var fragmentName: String? = null
    private val popoulateViewModel : PopulateViewModel by inject()
    private var position : Int? = null

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

        popoulateViewModel.populateViewState.observe(::getLifecycle,::observerPopulateData)
        popoulateViewModel.start()

        position = intent?.getIntExtra(POSITION,0)

        val presenter =  MainActivityPresenter(this,this, position = position)
        presenter.init()
    }

    fun observerPopulateData(populateViewState: PopulateViewState){
        when(populateViewState){
            is PopulateViewState.LoadingState -> {
                pgPopulate.visibility = View.VISIBLE
            }
            is PopulateViewState.SuccessState -> {
                pgPopulate.visibility = View.GONE
            }
        }
    }

    override fun onNext() {
        val position = vwpMain?.currentItem?.plus(1)

        position?.let {
            mainTabs.getTabAt(it)?.enableTouch()
            mainTabs.getTabAt(it)?.select()
        }
    }

    override fun goTo(index : Int) {
            mainTabs.getTabAt(index)?.enableTouch()
            mainTabs.getTabAt(index)?.select()
    }
}
