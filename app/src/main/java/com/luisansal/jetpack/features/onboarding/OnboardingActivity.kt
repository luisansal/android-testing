package com.luisansal.jetpack.features.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.luisansal.jetpack.R
import com.luisansal.jetpack.base.BaseActivity
import com.luisansal.jetpack.features.login.LoginActivity
import kotlinx.android.synthetic.main.activity_onboarding.*

class OnboardingActivity : BaseActivity(), PagerListener {
    override fun getViewIdResource() = R.layout.activity_onboarding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewpager.adapter = ViewPagerAdapter(supportFragmentManager)

        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) = Unit
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {}
        })
    }

    override fun onStartLogin() {
        finish()
    }
}
interface PagerListener {
    fun onStartLogin()
}