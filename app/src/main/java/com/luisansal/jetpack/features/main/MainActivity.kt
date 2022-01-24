package com.luisansal.jetpack.features.main

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.luisansal.jetpack.core.base.BaseBindingActivity
import com.luisansal.jetpack.databinding.ActivityMainBinding
import com.luisansal.jetpack.features.auth.LoginActivity
import com.luisansal.jetpack.features.auth.LoginViewState
import com.luisansal.jetpack.features.auth.login.LoginViewModel
import com.luisansal.jetpack.features.broadcastreciever.BroadcastReceiverActivity
import com.luisansal.jetpack.features.chat.ChatActivity
import com.luisansal.jetpack.features.design.DesignActivity
import com.luisansal.jetpack.features.manageusers.RoomActivity
import com.luisansal.jetpack.features.maps.MainMapsActivity
import com.luisansal.jetpack.features.menu.MenuActivity
import com.luisansal.jetpack.features.multimedia.MultimediaActivity
import com.luisansal.jetpack.features.onboarding.OnboardingEnum
import com.luisansal.jetpack.features.onboarding.OnboardingActivity
import com.luisansal.jetpack.features.sales.products.ProductActivity
import com.luisansal.jetpack.features.viewbinding.ViewBindingActivity
import com.luisansal.jetpack.features.viewpager.ViewPagerActivity
import com.luisansal.jetpack.features.workmanager.WorkManagerActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseBindingActivity() {

    companion object {
        const val PERMISSION_REQUEST_CODE = 4000
        const val POSITION = "position"
    }

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater).apply { lifecycleOwner = this@MainActivity }
    }

    private var position: Int? = null
    private val featuresAdapter by lazy {
        FeaturesAdapter {
            when (it) {
                FeaturesEnum.ROOM_USER_MANAGER -> {
                    startActivity(Intent(this, RoomActivity::class.java))
                }
                FeaturesEnum.MAPS -> {
                    startActivity(Intent(this, MainMapsActivity::class.java))
                }
                FeaturesEnum.MULTIMEDIA -> {
                    startActivity(Intent(this, MultimediaActivity::class.java))
                }
                FeaturesEnum.CHAT -> {
                    startActivity(Intent(this, ChatActivity::class.java))
                }
                FeaturesEnum.DESIGN -> {
                    startActivity(Intent(this, DesignActivity::class.java))
                }
                FeaturesEnum.ONBOARDING -> {
                    startActivity(OnboardingActivity.newIntent(this, OnboardingEnum.Basic))
                }
                FeaturesEnum.VIEW_PAGER -> {
                    startActivity(Intent(this, ViewPagerActivity::class.java))
                }
                FeaturesEnum.BROADCAST_RECEIVER -> {
                    startActivity(Intent(this, BroadcastReceiverActivity::class.java))
                }
                FeaturesEnum.VIEW_BINDING_COUNTDOWN -> {
                    startActivity(Intent(this, ViewBindingActivity::class.java))
                }
                FeaturesEnum.WORK_MANAGER -> {
                    startActivity(Intent(this, WorkManagerActivity::class.java))
                }
                FeaturesEnum.ALARM_MANAGER -> {
                    startActivity(Intent(this, OnboardingActivity::class.java))
                }
                FeaturesEnum.SALES_MANAGER -> {
                    startActivity(Intent(this, ProductActivity::class.java))
                }
                FeaturesEnum.MENU -> {
                    startActivity(MenuActivity.newIntent(this))
                }
            }
        }
    }

    private val loginViewModel by viewModel<LoginViewModel>()
    override fun getViewResource() = binding.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissionsWriteRead()
        position = intent?.getIntExtra(POSITION, 0)
        manageIntent()

        binding.rvFeatures.layoutManager = LinearLayoutManager(this)
        val data = mutableListOf<FeaturesEnum>()
        data.add(FeaturesEnum.ROOM_USER_MANAGER)
        data.add(FeaturesEnum.MAPS)
        data.add(FeaturesEnum.MULTIMEDIA)
        data.add(FeaturesEnum.CHAT)
        data.add(FeaturesEnum.DESIGN)
        data.add(FeaturesEnum.ONBOARDING)
        data.add(FeaturesEnum.VIEW_PAGER)
        data.add(FeaturesEnum.VIEW_BINDING_COUNTDOWN)
        data.add(FeaturesEnum.BROADCAST_RECEIVER)
        data.add(FeaturesEnum.WORK_MANAGER)
        data.add(FeaturesEnum.SALES_MANAGER)
        data.add(FeaturesEnum.MENU)

        featuresAdapter.dataSet = data
        binding.rvFeatures.adapter = featuresAdapter

        loginViewModel.loginViewState.observe(this, Observer {
            if (it is LoginViewState.LogoutSuccessState) {
                finish()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        })
        onClickLogout()
    }

    private fun requestPermissionsWriteRead() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_REQUEST_CODE
        )
    }

    private fun manageIntent() {
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

    private fun handleSendText(intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            // Update UI to reflect text being shared
            Toast.makeText(this, "enlace recibido $it", Toast.LENGTH_LONG).show()
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

    private fun onClickLogout() {
        binding.btnLogout.setOnClickListener {
            loginViewModel.logout()
        }
    }

}
