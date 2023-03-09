package fhict.sm.sleepdeprived

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import fhict.sm.sleepdeprived.data.sleep.SleepRequestsManager

class BootReceiver : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        val sleepRequestManager = SleepRequestsManager(context)

        sleepRequestManager.requestSleepUpdates(requestPermission = {
            Log.d(TAG, "Permission to listen for Sleep Activity has been removed")
        })
    }

    companion object {

        private const val TAG = "SLEEP_BOOT_RECEIVER"
    }
}