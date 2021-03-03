package com.luisansal.jetpack.features.viewbinding

import android.os.CountDownTimer
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luisansal.jetpack.data.preferences.UserSharedPreferences
import com.luisansal.jetpack.utils.OnClickKeyListener
import java.util.*

class ViewBindingViewModel(private val userSharedP: UserSharedPreferences) : ViewModel() {
    companion object {
        const val TIME_TO_COUNTDOWN = 20000L
        const val TIME_INTERVAL = 1000L
    }

    var position = 0

    val et1Str = MutableLiveData<String>("-")
    val et2Str = MutableLiveData<String>("-")
    val et3Str = MutableLiveData<String>("-")
    val et4Str = MutableLiveData<String>("-")

    val listEts = listOf(et1Str, et2Str, et3Str, et4Str)
    val keyBoardPosState = MutableLiveData<Boolean>(false)

    val onKeyClick: OnClickKeyListener = object : OnClickKeyListener {
        override fun onClickKey(digit: Int) {
            receiveDigit(digit)
        }
    }

    val waitingTime = MutableLiveData<Long>()
    var isStarted = false
    var countDownTimer: CountDownTimer? = null

    fun onTouchLitener(index: Int) = View.OnTouchListener { _, _ ->
        reset()
        onTextClick(index)
        keyBoardPosState.postValue(true)
        true
    }

    fun receiveDigit(numberPushed: Int) {

        if (numberPushed < 0 && position > 0) {
            position--
            doAction(Pair(numberPushed.toString(), position))
        } else {
            doAction(Pair(numberPushed.toString(), position))
            position++
        }

    }

    private fun doAction(it: Pair<String, Int>) {
        var value = it.first
        val position = it.second
        if (value == "-1") {
            value = "-"
        }
        when (position) {
            0 -> {
                et1Str.postValue(value)
                if (value != "|" && value != "-")
                    et2Str.postValue("|")
            }
            1 -> {
                et2Str.postValue(value)
                if (value != "|" && value != "-")
                    et3Str.postValue("|")
            }
            2 -> {
                et3Str.postValue(value)
                if (value != "|" && value != "-")
                    et4Str.postValue("|")
            }
            3 -> {
                et4Str.postValue(value)
            }
        }
    }

    fun onTextClick(_position: Int) {
        val value = "|"
        position = _position
        doAction(Pair(value, position))
    }

    fun reset() {
        listEts.forEachIndexed { index, et ->
            if (et.value == "|")
                et.postValue("-")
        }
    }

    fun onStartCountDown() {
        userSharedP.countDownStartTime = Calendar.getInstance().timeInMillis
        if (userSharedP.countDownEndTime == 0L)
            userSharedP.countDownEndTime = Calendar.getInstance().timeInMillis

        val starTime = userSharedP.countDownStartTime
        val endTime = userSharedP.countDownEndTime
        if (starTime > endTime && userSharedP.timeToCountDown >= TIME_INTERVAL) {
            userSharedP.countDownEndTime = starTime
            userSharedP.countDownStartTime = endTime
        } else {
            userSharedP.timeToCountDown = TIME_TO_COUNTDOWN
        }
        if (userSharedP.timeToCountDown <= TIME_INTERVAL)
            userSharedP.timeToCountDown = TIME_TO_COUNTDOWN
        isStarted = true
    }

    fun onResumeCountDown() {
        if (!isStarted)
            onStartCountDown()

        val diff = userSharedP.countDownEndTime - userSharedP.countDownStartTime
        var timeToCountDown = userSharedP.timeToCountDown - diff

        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(timeToCountDown, TIME_INTERVAL) {
            override fun onFinish() {}

            override fun onTick(p0: Long) {
                userSharedP.timeToCountDown = p0
                waitingTime.postValue(p0)
            }
        }
        countDownTimer?.start()
    }

    fun onStopCountDown() {
        userSharedP.countDownEndTime = Calendar.getInstance().timeInMillis
        isStarted = false
        countDownTimer?.cancel()
    }

    fun onDestroyCountDown() {
        userSharedP.countDownEndTime = Calendar.getInstance().timeInMillis
        countDownTimer?.cancel()
    }
}