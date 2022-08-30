package com.luisansal.core.test

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.luisansal.jetpack.core.base.BaseBindingActivity

@SuppressLint("Registered")
class EmptyActivity : BaseBindingActivity(){

    var trackingChangedCalled: Boolean = false
        private set
    var updateDrawerContentCalled: Boolean = false
        private set

    override val activityModules: Kodein.Module
        get() = module {
            overriding(MainModule(lifecycle))
            overriding(DiaryModule())
            overriding(TrackerModule())
            overriding(DigitalReportModule())
            overriding(TreatmentModule())
            overriding(ProfileModule())
            overriding(IndividualMapsModule())
            overriding(PersonalReportModule())
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resetProgress()
    }

    fun configureBinding(binding: ActivityMainBinding) {
    }

    override val layoutId = R.layout.activity_main

    override fun setContentFrame(
        fragment: Fragment,
        preserveHistory: Boolean,
        resetBackStack: Boolean,
        allowStateLoss: Boolean
    ) {
        runOnUiThread {
            supportFragmentManager.beginTransaction()
                .add(R.id.content_frame, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    fun close() {
        supportFragmentManager.popBackStack()
        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        }
    }

    companion object {
        fun open(context: Context) = context.startActivity(EmptyActivity::class)
    }
}
