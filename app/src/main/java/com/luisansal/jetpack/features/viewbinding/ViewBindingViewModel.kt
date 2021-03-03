package com.luisansal.jetpack.features.viewbinding

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luisansal.jetpack.utils.OnClickKeyListener

class ViewBindingViewModel: ViewModel() {

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
//                if (value != "|" && value != "-")
//                    et5Str.postValue("|")
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
}