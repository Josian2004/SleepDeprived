package fhict.sm.sleepdeprived.data.sleep

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.SleepClassifyEvent
import com.google.android.gms.location.SleepSegmentEvent
import dagger.hilt.android.AndroidEntryPoint
import fhict.sm.sleepdeprived.data.sleep.db.SleepSegmentEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class SleepReceiver : BroadcastReceiver() {

    private val scope: CoroutineScope = MainScope()
    @Inject lateinit var sleepRepository: SleepRepository

    override fun onReceive(context: Context, intent: Intent) {
        if (SleepSegmentEvent.hasEvents(intent)) {
            val events = SleepSegmentEvent.extractEvents(intent)

            Log.d(TAG, "Logging SleepSegmentEvents")
            for (event in events) {
                addSleepSegmentToDatabase(event)
                Log.d(
                    TAG,
                    "${event.startTimeMillis} to ${event.endTimeMillis} with status ${event.status} for ${event.segmentDurationMillis}")
            }
        } else if (SleepClassifyEvent.hasEvents(intent)) {
            val events = SleepClassifyEvent.extractEvents(intent)

            Log.d(TAG, "Logging SleepClassifyEvents")
            for (event in events) {
                Log.d(
                    TAG,
                    "Confidence: ${event.confidence} - Light: ${event.light} - Motion: ${event.motion}")
            }
        }
    }

    private fun defineDateOfNight(sleepSegmentEvent: SleepSegmentEvent): String {
        val startDay = SimpleDateFormat("dd/MM/yyyy").format(Date(sleepSegmentEvent.startTimeMillis))
        val endDay = SimpleDateFormat("dd/MM/yyyy").format(Date(sleepSegmentEvent.endTimeMillis))
        return if (startDay.equals(endDay)) {
            SimpleDateFormat("dd/MM/yyyy").format(Date(sleepSegmentEvent.startTimeMillis - 86400000))
        } else {
            startDay
        }
    }

    private fun addSleepSegmentToDatabase(sleepSegmentEvent: SleepSegmentEvent) {
        scope.launch {
            val dateOfNight = defineDateOfNight(sleepSegmentEvent)
            if (sleepRepository.getSleepSegmentByNight(dateOfNight) == null) {
                sleepRepository.saveSleepSegment(SleepSegmentEntity(dateOfNight, sleepSegmentEvent.startTimeMillis, sleepSegmentEvent.endTimeMillis, sleepSegmentEvent.segmentDurationMillis, sleepSegmentEvent.status, 0f, false))
            }
        }
    }

    companion object {
        private const val TAG = "SLEEP_RECEIVER"
        fun createPendingIntent(context: Context): PendingIntent {
            val intent = Intent(context, SleepReceiver::class.java)
            return PendingIntent.getBroadcast(context,
                0, intent, PendingIntent.FLAG_MUTABLE)
        }
    }
}