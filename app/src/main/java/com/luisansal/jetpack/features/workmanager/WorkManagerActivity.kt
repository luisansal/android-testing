package com.luisansal.jetpack.features.workmanager

import android.os.CountDownTimer
import android.view.View
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.luisansal.jetpack.R
import com.luisansal.jetpack.core.base.BaseBindingActivity
import com.luisansal.jetpack.databinding.ActivityWorkmanagerBinding
import java.time.Duration

class WorkManagerActivity : BaseBindingActivity() {
    private val binding by lazy {
        ActivityWorkmanagerBinding.inflate(layoutInflater).apply { lifecycleOwner = this@WorkManagerActivity }
    }
    private val mWork by lazy {
        OneTimeWorkRequest.Builder(CalculatorWorker::class.java)
            .setInitialDelay(Duration.ofSeconds(3))
            .setConstraints(Constraints.Builder().setRequiresCharging(true).build())
            .addTag("MY_CALCULATOR")
            .build()
    }

    override fun getViewResource() = binding.root

    fun onclickStartWorkManager(view: View) {
        binding.tvInfo2.visibility = View.VISIBLE
        val countDownTimer = object : CountDownTimer(3000, 1000) {
            override fun onFinish() = Unit
            override fun onTick(tickTime: Long) {
                binding.tvInfo2.text = String.format(getString(R.string.seconds_remaining), tickTime / 1000)
            }
        }
        countDownTimer.start()
        WorkManager.getInstance().enqueue(mWork)
    }

    fun onclickCancel(view: View) {
        WorkManager.getInstance().cancelWorkById(mWork.id)
    }

}