package com.luisansal.jetpack.features.viewbinding

import android.content.Context
import android.os.CountDownTimer
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luisansal.jetpack.R
import com.luisansal.jetpack.data.preferences.UserSharedPreferences
import com.luisansal.jetpack.utils.OnClickKeyListener
import java.util.*

class ViewBindingViewModel(private val userSharedP: UserSharedPreferences, private val context: Context) : ViewModel() {
    companion object {
        const val TIME_TO_COUNTDOWN = 20000L
        const val TIME_INTERVAL = 1000L
    }

    var position = 0

    val et1Str = MutableLiveData<String>("-")
    val et2Str = MutableLiveData<String>("-")
    val et3Str = MutableLiveData<String>("-")
    val et4Str = MutableLiveData<String>("-")
    val title = MutableLiveData<String>(context.getString(R.string.lorem_small))
    val isBackClicked = MutableLiveData<Boolean>(false)
    val listEts = listOf(et1Str, et2Str, et3Str, et4Str)
    val keyBoardPosState = MutableLiveData<Boolean>(false)

    val onKeyClick: OnClickKeyListener = object : OnClickKeyListener {
        override fun onClickKey(digit: Int) {
            receiveDigit(digit)
        }
    }
    val isValidated = MutableLiveData<Boolean>(false)

    val waitingTime = MutableLiveData<Long>()
    var isStarted = false
    var isFinished = false
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
            doOnRecieveDigit(Pair(numberPushed.toString(), position))
        } else {
            if (position > listEts.size - 1)
                return
            doOnRecieveDigit(Pair(numberPushed.toString(), position))
            position++
        }
    }

    private fun doOnRecieveDigit(it: Pair<String, Int>) {
        var value = it.first
        val position = it.second
        if (value == "-1") {
            value = "-"
        }
        when (position) {
            0 -> {
                et1Str.value = value
                if (value != "|" && value != "-")
                    et2Str.value = "|"
            }
            1 -> {
                et2Str.postValue(value)
                if (value != "|" && value != "-")
                    et3Str.value = "|"
            }
            2 -> {
                et3Str.postValue(value)
                if (value != "|" && value != "-")
                    et4Str.value = "|"
            }
            3 -> {
                et4Str.value = value
            }
        }
        isValidated.postValue(validate())
    }

    fun onTextClick(_position: Int) {
        val value = "|"
        position = _position
        doOnRecieveDigit(Pair(value, position))
    }

    private fun reset() {
        listEts.forEachIndexed { index, et ->
            if (et.value == "|")
                et.postValue("-")
        }
    }

    fun onStartCountDown() {
        configStartEndTime()
        isStarted = true
    }

    private fun configStartEndTime() {
        userSharedP.countDownStartTime = Calendar.getInstance().timeInMillis
        if (userSharedP.countDownEndTime == 0L)
            userSharedP.countDownEndTime = Calendar.getInstance().timeInMillis
        val starTime = userSharedP.countDownStartTime
        val endTime = userSharedP.countDownEndTime
        if (starTime > endTime && userSharedP.timeToCountDown >= TIME_INTERVAL) {
            userSharedP.countDownEndTime = starTime
            userSharedP.countDownStartTime = endTime
        }
    }

    fun onResumeCountDown() {
        if (isFinished)
            return

        if (!isStarted)
            configStartEndTime()

        val diff = userSharedP.countDownEndTime - userSharedP.countDownStartTime
        var timeToCountDown = userSharedP.timeToCountDown - diff
        if (timeToCountDown <= TIME_INTERVAL && isStarted)
            timeToCountDown = TIME_TO_COUNTDOWN
        if (timeToCountDown > TIME_TO_COUNTDOWN)
            timeToCountDown = TIME_TO_COUNTDOWN

        startCountDown(timeToCountDown)
    }

    private fun startCountDown(timeToCountDown: Long) {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(timeToCountDown, TIME_INTERVAL) {
            override fun onFinish() {
                isFinished = true
                isValidated.postValue(false)
                userSharedP.timeToCountDown = 0
            }

            override fun onTick(tickTime: Long) {
                userSharedP.timeToCountDown = tickTime
                waitingTime.postValue(tickTime)
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

    fun onResendCode() {
        isFinished = false
        startCountDown(TIME_TO_COUNTDOWN)
    }

    private fun validate(): Boolean {
        listEts.forEachIndexed { index, et ->
            if (et.value == "|" || et.value == "-")
                return false
        }
        return true
    }

    fun onClickBack(): View.OnClickListener {
        return View.OnClickListener {
            isBackClicked.postValue(true)
        }
    }
}