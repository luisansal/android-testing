package com.luisansal.jetpack

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
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

    companion object {
        const val PERMISSION_REQUEST_CODE = 4000
        const val POSITION = "position"
    }

    override var fragmentName: String? = null
    private val popoulateViewModel: PopulateViewModel by inject()
    private var position: Int? = null

    override fun setupTabPager() {
        mainTabs.setupWithViewPager(vwpMain)
        mainTabs.setTabMode(TabLayout.MODE_SCROLLABLE)
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

        popoulateViewModel.populateViewState.observe(::getLifecycle, ::observerPopulateData)
        popoulateViewModel.start()

        position = intent?.getIntExtra(POSITION, 0)

        val presenter = MainActivityPresenter(this, this, position = position)
        presenter.init()

        manageIntent()
    }

    fun manageIntent() {
        when {
            intent?.action == Intent.ACTION_SEND -> {
                if ("text/plain" == intent.type) {
                    handleSendText(intent) // Handle text being sent
                } else if (intent.type?.startsWith("image/") == true) {
                    handleSendImage(intent) // Handle single image being sent
                }
            }
            intent?.action == Intent.ACTION_SEND_MULTIPLE
                    && intent.type?.startsWith("image/") == true -> {
                handleSendMultipleImages(intent) // Handle multiple images being sent
            }
            else -> {
                // Handle other intents, such as being started from the home screen
            }
        }
    }

    fun observerPopulateData(populateViewState: PopulateViewState) {
        when (populateViewState) {
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
            goTo(it)
        }
    }

    private fun handleSendText(intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            // Update UI to reflect text being shared
            Toast.makeText(applicationContext, "enlace recibido $it", Toast.LENGTH_LONG).show()
        }
    }

    private fun handleSendImage(intent: Intent) {
        (intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as? Uri)?.let {
            // Update UI to reflect image being shared
        }
    }

    private fun handleSendMultipleImages(intent: Intent) {
        intent.getParcelableArrayListExtra<Parcelable>(Intent.EXTRA_STREAM)?.let {
            // Update UI to reflect multiple images being shared
        }
    }


    override fun goTo(index: Int) {
        mainTabs.getTabAt(index)?.enableTouch()
        mainTabs.getTabAt(index)?.select()
    }
}
