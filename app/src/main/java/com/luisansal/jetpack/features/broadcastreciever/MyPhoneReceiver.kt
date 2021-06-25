package com.luisansal.jetpack.features.broadcastreciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast


class MyPhoneReceiver : BroadcastReceiver() {
    companion object {
        private const val TAG = "MyPhoneReceiver"
    }
    override fun onReceive(context: Context?, intent: Intent) {
        val extras = intent.extras
        if (extras != null) {
            val state = extras.getString(TelephonyManager.EXTRA_STATE)
            Log.w("MY_DEBUG_TAG", "$state")
            if (state == TelephonyManager.EXTRA_STATE_RINGING) {
                val phoneNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER)
                Log.w("MY_DEBUG_TAG", "$phoneNumber")
            }
        }
        Toast.makeText(context, TAG, Toast.LENGTH_LONG).show()
    }
}