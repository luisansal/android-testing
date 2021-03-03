package com.luisansal.jetpack.features.broadcastreciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Vibrator
import android.provider.Telephony
import android.telephony.SmsMessage
import android.widget.Toast


class SmsBroadcastReceiver : BroadcastReceiver() {
    companion object {
        private const val TAG = "SmsBroadcastReceiver"
        const val SMS_RECEIVED = "SmsBroadcastReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action.equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            val bundle = intent.extras
            if (bundle != null) {
                // get sms objects
                val pdus = bundle["pdus"] as Array<Any>?
                if (pdus!!.size == 0) {
                    return
                }
                // large message might be broken into many
                val messages: Array<SmsMessage?> = arrayOfNulls(pdus!!.size)
                val sb = StringBuilder()
                for (i in pdus!!.indices) {
                    messages[i] = SmsMessage.createFromPdu(pdus!![i] as ByteArray)
                    sb.append(messages[i]?.messageBody)
                }
                val sender: String = messages[0]?.originatingAddress!!
                val message = sb.toString()
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                // prevent any other broadcast receivers from receiving broadcast
                // abortBroadcast();
            }

            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(2000)
        }
    }


}