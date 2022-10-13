package com.luisansal.jetpack.core.test.espresso

import androidx.test.espresso.IdlingResource
import androidx.viewpager.widget.ViewPager

class ViewPagerIdlingResource(
    viewPager: ViewPager,
    private val resourceName: String = "ViewPagerIdlingResource"
) : IdlingResource, ViewPager.SimpleOnPageChangeListener() {

    private var idle = true
    private var callback: IdlingResource.ResourceCallback? = null

    init {
        viewPager.addOnPageChangeListener(this)
    }

    override fun getName(): String = resourceName
    override fun isIdleNow(): Boolean = idle

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.callback = callback
    }

    override fun onPageScrollStateChanged(state: Int) {
        idle = state == androidx.viewpager.widget.ViewPager.SCROLL_STATE_IDLE ||
            state == androidx.viewpager.widget.ViewPager.SCROLL_STATE_DRAGGING

        if (idle) {
            callback?.onTransitionToIdle()
        }
    }
}
