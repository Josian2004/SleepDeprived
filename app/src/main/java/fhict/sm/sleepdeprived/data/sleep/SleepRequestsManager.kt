package fhict.sm.sleepdeprived.data.sleep

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.ActivityRecognition
import com.google.android.gms.location.SleepSegmentRequest

class SleepRequestsManager(private val context: Context) {

    private val sleepReceiverPendingIntent by lazy {
        SleepReceiver.createPendingIntent(context)
    }

    fun requestSleepUpdates(requestPermission: () -> Unit = {}) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACTIVITY_RECOGNITION) ==
            PackageManager.PERMISSION_GRANTED) {
            subscribeToSleepUpdates()
        } else {
            requestPermission()
        }
    }

    @SuppressLint("MissingPermission")
    fun subscribeToSleepUpdates() {
        ActivityRecognition.getClient(context)
            .requestSleepSegmentUpdates(sleepReceiverPendingIntent,
                SleepSegmentRequest.getDefaultSleepSegmentRequest())
    }

    fun unsubscribeFromSleepUpdates() {
        ActivityRecognition.getClient(context)
            .removeSleepSegmentUpdates(sleepReceiverPendingIntent)
    }
}