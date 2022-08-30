package com.luisansal.core.test.actions

import android.view.View
import android.view.ViewConfiguration
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.CoordinatesProvider
import androidx.test.espresso.action.PrecisionDescriber
import androidx.test.espresso.action.Swiper
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.util.HumanReadables
import org.hamcrest.Matcher

/**
 * @see {package android.support.com.luisansal.core.test.espresso.action.GeneralSwipeAction}
 *
 * This class overrides the hardcoded requirement that forces the swipeable view
 * to be 90% displayed on the device.
 */
class GeneralSwipeAction(
    private val swiper: Swiper,
    private val startCoordinatesProvider: CoordinatesProvider,
    private val endCoordinatesProvider: CoordinatesProvider,
    private val precisionDescriber: PrecisionDescriber
) : ViewAction {

    override fun getConstraints(): Matcher<View> {
        return isEnabled()
    }

    override fun perform(uiController: UiController, view: View) {
        val startCoordinates = startCoordinatesProvider.calculateCoordinates(view)
        val endCoordinates = endCoordinatesProvider.calculateCoordinates(view)
        val precision = precisionDescriber.describePrecision()

        var status: Swiper.Status = Swiper.Status.FAILURE

        var tries = 0
        while (tries < MAX_TRIES && status != Swiper.Status.SUCCESS) {
            try {
                status = swiper.sendSwipe(uiController, startCoordinates, endCoordinates, precision)
            } catch (re: RuntimeException) {
                throw PerformException.Builder()
                    .withActionDescription(this.description)
                    .withViewDescription(HumanReadables.describe(view))
                    .withCause(re)
                    .build()
            }

            val duration = ViewConfiguration.getPressedStateDuration()
            // ensures that all work enqueued to process the swipe has been run.
            if (duration > 0) {
                uiController.loopMainThreadForAtLeast(duration.toLong())
            }
            tries++
        }

        if (status == Swiper.Status.FAILURE) {
            throw PerformException.Builder()
                .withActionDescription(description)
                .withViewDescription(HumanReadables.describe(view))
                .withCause(RuntimeException(String.format(
                    "Couldn't swipe from: %s,%s to: %s,%s precision: %s, %s ." +
                        " Swiper: %s " + "start coordinate provider: %s precision" +
                        " describer: %s. Tried %s times",
                    startCoordinates[0],
                    startCoordinates[1],
                    endCoordinates[0],
                    endCoordinates[1],
                    precision[0],
                    precision[1],
                    swiper,
                    startCoordinatesProvider,
                    precisionDescriber,
                    MAX_TRIES
                )))
                .build()
        }
    }

    override fun getDescription(): String {
        return swiper.toString().toLowerCase() + " swipe"
    }

    companion object {
        /** Maximum number of times to attempt sending a swipe action.  */
        private const val MAX_TRIES = 3
    }
}
