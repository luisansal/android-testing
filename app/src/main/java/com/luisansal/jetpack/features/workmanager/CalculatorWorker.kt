package com.luisansal.jetpack.features.workmanager

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters

class CalculatorWorker(private val context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        var text = ""

        Handler(Looper.getMainLooper()).postDelayed({
            for (x: Int in 1..2) {
                for (y: Int in 1..10) {
                    text += "$x x $y = ${x * y}"
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                    text = ""
                }
            }
        }, 500)


        return Result.success()
    }
}