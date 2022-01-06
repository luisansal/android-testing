package com.luisansal.jetpack.features.broadcastreciever

import android.Manifest
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Telephony
import android.telephony.TelephonyManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.luisansal.jetpack.R
import com.luisansal.jetpack.core.base.BaseActivity
import kotlinx.android.synthetic.main.activity_broadcastreceiver.*


class BroadcastReceiverActivity : BaseActivity() {
    companion object {
        private const val PERMISSIONS_REQUEST_SMS_RECEIVE = 1
        private const val PERMISSIONS_REQUEST_CALL_RECEIVE = 2
    }
    override fun getViewIdResource() = R.layout.activity_broadcastreceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btnSMS.setOnClickListener {
            requireSMSPermission()
        }
        btnCall.setOnClickListener {
            requireCallPermission()
        }
    }

    private fun requireSMSPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECEIVE_SMS), PERMISSIONS_REQUEST_SMS_RECEIVE)
        } else {
            registerSMSReceiver()
        }
    }

    private fun registerSMSReceiver(){
        val filter = IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION).apply {
            addAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
        }
        registerReceiver(SmsBroadcastReceiver(), filter)
        showMessage("SMS Receiver iniciado")
    }

    private fun requireCallPermission(){
        //READ_PHONE_STATE
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE), PERMISSIONS_REQUEST_CALL_RECEIVE)
        } else {
            registerCallReceiver()
        }
    }

    private fun registerCallReceiver(){
        val filter = IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED).apply {
            addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
        }
        registerReceiver(MyPhoneReceiver(), filter)
        showMessage("CALL Receiver iniciado")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_SMS_RECEIVE) {
            registerSMSReceiver()
        }
        if (requestCode == PERMISSIONS_REQUEST_CALL_RECEIVE) {
            registerCallReceiver()
        }
    }
}