package pe.com.luisansal.core.test

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestName
import org.junit.runner.RunWith
import pe.com.luisansal.core.test.actions.clearFocus
import pe.com.luisansal.core.test.assume.TestFilter

@LargeTest
@RunWith(AndroidJUnit4::class)
abstract class ActivityTest<T : Activity>(clazz: Class<T>) :
    AppInjectedTest(),
    ScreenshotTest,
    TestFilter {

    @Rule
    @JvmField
    val testRule: IntentsTestRule<T> = IntentsTestRule(clazz, true, false)

    @Rule
    @JvmField
    val testName = TestName()

    @Before
    fun before() {
        verifyTypeOfTestIsCorrect(testName.methodName)
    }

    open fun startActivity(
        args: Bundle = Bundle(),
        shouldClearFocus: Boolean = true
    ): T {
        val intent = Intent()
        intent.putExtras(args)
        val activity = testRule.launchActivity(intent)
        if (shouldClearFocus) {
            clearFocus()
        }
        return activity
    }

    fun pauseAndResumeActivity(activity: Activity) {
        EmptyActivity.open(activity)
        Espresso.pressBack()
    }

    fun givenAsyncActionsNeverFinish(mode: Filter = Filter.All) {
//        coroutineHandler.shouldBlockMessage = when (mode) {
//            Filter.All -> {
//                { true }
//            }
//            is Filter.At -> {
//                { it == mode.index }
//            }
//            is Filter.After -> {
//                { it >= mode.index }
//            }
//        }
    }

    val currentActivity: Activity
        get() {
            val activity = arrayOfNulls<Activity>(1)
            onView(isRoot()).check { view, _ -> activity[0] = view.context as Activity }
            return activity[0]!!
        }

    sealed class Filter {
        object All : Filter()
        data class At(val index: Int) : Filter()
        data class After(val index: Int) : Filter()
    }
}
