package com.luisansal.jetpack.features.broadcastreciever

import android.Manifest
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Telephony
import android.telephony.TelephonyManager
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.luisansal.jetpack.R
import com.luisansal.jetpack.core.base.BaseActivity
import com.luisansal.jetpack.core.base.BaseBindingActivity
import com.luisansal.jetpack.databinding.ActivityBroadcastreceiverBinding


class BroadcastReceiverActivity : BaseBindingActivity() {

    companion object {
        private const val PERMISSIONS_REQUEST_SMS_RECEIVE = 1
        private const val PERMISSIONS_REQUEST_CALL_RECEIVE = 2
    }
    val binding by lazy {
        ActivityBroadcastreceiverBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@BroadcastReceiverActivity
        }
    }

    override fun getViewResource() = binding.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnSMS.setOnClickListener {
            requireSMSPermission()
        }
        binding.btnCall.setOnClickListener {
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