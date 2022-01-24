package com.luisansal.jetpack.features.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.luisansal.jetpack.core.base.BaseBindingActivity
import com.luisansal.jetpack.databinding.ActivityOnboardingBinding

class OnboardingActivity : BaseBindingActivity(), PagerListener {

    companion object {
        const val TYPE = "TYPE"
        fun newIntent(context: Context, type: OnboardingEnum) = Intent(context, OnboardingActivity::class.java).apply {
            putExtra(TYPE, type)
        }
    }

    private val type: OnboardingEnum by lazy { intent.extras?.get(TYPE) as OnboardingEnum }

    private val binding by lazy {
        ActivityOnboardingBinding.inflate(layoutInflater).apply { lifecycleOwner = this@OnboardingActivity }
    }

    override fun getViewResource() = binding.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewpager.adapter = ViewPagerAdapter(supportFragmentManager, type)
        binding.viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
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