package com.luisansal.core.test

import android.R
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.filters.LargeTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.test.espresso.Espresso
import com.luisansal.core.test.assume.TestFilter
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestName
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
abstract class FragmentTest(
    private val fragmentProvider: () -> Fragment
) : AppInjectedTest(),
    ScreenshotTest,
    TestFilter {

    @Rule
    @JvmField
    val testRule: IntentsTestRule<EmptyActivity> =
        IntentsTestRule(EmptyActivity::class.java, true, false)

    @Rule
    @JvmField
    val testName = TestName()

    @Before
    fun before() {
        verifyTypeOfTestIsCorrect(testName.methodName)
    }

    open fun startFragment(
        fragmentUnderTest: Fragment? = null,
        args: Bundle = Bundle(),
        shouldClearFocus: Boolean = true
    ): FragmentActivity {
        val intent = Intent()
        intent.putExtras(args)
        val activity = testRule.launchActivity(intent)
        val fragment = fragmentUnderTest ?: fragmentProvider()
        fragment.arguments = args
        activity.setContentFrame(fragment)
        assertDisplayed(R.id.content)
        return activity
    }

    fun compareFragment(activity: FragmentActivity, heightInPx: Int) {
        assertDisplayed(R.id.content)
        val fragment = activity.supportFragmentManager.fragments.first()
        compareScreenshot(fragment, heightInPx)
    }

    open fun givenAsyncActionsNeverFinish(mode: ActivityTest.Filter = ActivityTest.Filter.All) {
        coroutineHandler.shouldBlockMessage = when (mode) {
            ActivityTest.Filter.All -> {
                { true }
            }
            is ActivityTest.Filter.At -> {
                { it == mode.index }
            }
            is ActivityTest.Filter.After -> {
                { it >= mode.index }
            }
        }
    }

    fun pauseAndResumeActivity(activity: Activity) {
        EmptyActivity.open(activity)
        Espresso.pressBack()
    }
}
