package pe.com.luisansal.core.test.actions

import android.view.View
import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.CoordinatesProvider
import androidx.test.espresso.action.Press
import androidx.test.espresso.action.Swipe
import androidx.test.espresso.action.ViewActions.actionWithAssertions
import androidx.test.espresso.matcher.ViewMatchers.withId

fun swipeViewPagerBack(@IdRes id: Int) {
    onView(withId(id)).perform(swipeRight())
}

fun swipeRight(): ViewAction {
    val almostLeft = 1 / 6f
    val right = 1f
    val center = 1 / 2f

    return actionWithAssertions(
        GeneralSwipeAction(
        Swipe.FAST,
        NormalizedLocation(almostLeft, center),
        NormalizedLocation(right, center),
        Press.FINGER)
    )
}

private class NormalizedLocation(
    val normalizedXPosition: Float,
    val normalizedYPosition: Float
) : CoordinatesProvider {

    override fun calculateCoordinates(view: View?): FloatArray {
        view ?: return floatArrayOf(0f, 0f)

        val xy = IntArray(2)
        view.getLocationOnScreen(xy)
        val x = xy[0] + view.width * normalizedXPosition
        val y = xy[1] + view.height * normalizedYPosition
        return floatArrayOf(x, y)
    }
}
