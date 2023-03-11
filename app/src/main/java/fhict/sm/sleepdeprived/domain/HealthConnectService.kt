package fhict.sm.sleepdeprived.domain

import android.content.Context
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.SleepStageRecord
import androidx.health.connect.client.request.AggregateRequest
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import dagger.hilt.android.qualifiers.ApplicationContext
import fhict.sm.sleepdeprived.data.sleep.SleepRepository
import fhict.sm.sleepdeprived.data.sleep.db.SleepSegmentEntity
import fhict.sm.sleepdeprived.data.sleep.db.SleepStageEntity
import fhict.sm.sleepdeprived.data.sleep.db.StageType
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList

@Singleton
class HealthConnectService @Inject constructor (
    @ApplicationContext private val context: Context,
    private val sleepRepository: SleepRepository
    ) {
    private val healthConnectClient by lazy { HealthConnectClient.getOrCreate(context) }

    private val permissions =
        setOf(
            HealthPermission.getReadPermission(SleepSessionRecord::class),
            HealthPermission.getReadPermission(SleepStageRecord::class)
        )

    suspend fun hasAllPermissions(): Boolean {
        return healthConnectClient.permissionController.getGrantedPermissions().containsAll(permissions)
    }

    fun requestPermissionsActivityContract(): ActivityResultContract<Set<String>, Set<String>> {
        return PermissionController.createRequestPermissionResultContract()
    }

    suspend fun revokeAllPermissions(){
        healthConnectClient.permissionController.revokeAllPermissions()
    }


    suspend fun getSleepSessionData() {
        Log.d("HEALTH", "Getting sleep data")
        val lastDay = ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS)
            .withHour(23)

        val firstDay = lastDay
            .minusDays(30)

        val sessions = mutableListOf<SleepSegmentEntity>()
        val sleepSessionRequest = ReadRecordsRequest(
            recordType = SleepSessionRecord::class,
            timeRangeFilter = TimeRangeFilter.between(firstDay.toInstant(), lastDay.toInstant()),
            ascendingOrder = false
        )
        val sleepSessions = healthConnectClient.readRecords(sleepSessionRequest)
        sleepSessions.records.forEach { session ->
            val sessionTimeFilter = TimeRangeFilter.between(session.startTime, session.endTime)
            val durationAggregateRequest = AggregateRequest(
                metrics = setOf(SleepSessionRecord.SLEEP_DURATION_TOTAL),
                timeRangeFilter = sessionTimeFilter
            )
            val aggregateResponse = healthConnectClient.aggregate(durationAggregateRequest)
            val stagesRequest = ReadRecordsRequest(
                recordType = SleepStageRecord::class,
                timeRangeFilter = sessionTimeFilter
            )
            val stagesResponse = healthConnectClient.readRecords(stagesRequest)
            val sleepStages: ArrayList<SleepStageEntity> = ArrayList()
            stagesResponse.records.forEach { stage ->
                var stageType: StageType = StageType.UNKNOWN
                when (stage.stage) {
                    0 -> stageType = StageType.UNKNOWN
                    1 -> stageType = StageType.AWAKE
                    2 -> stageType = StageType.SLEEPING
                    3 -> stageType = StageType.OUT_OF_BED
                    4 -> stageType = StageType.LIGHT
                    5 -> stageType = StageType.DEEP
                    6 -> stageType = StageType.REM
                }
                val stageDuration = stage.endTime.toEpochMilli() - stage.startTime.toEpochMilli()
                if (stageDuration > 60000) {
                    sleepStages.add(SleepStageEntity(
                        startTime = stage.startTime.toEpochMilli(),
                        endTime = stage.endTime.toEpochMilli(),
                        duration = stageDuration,
                        type = stageType,
                        sleepSegment = SimpleDateFormat("dd/MM/yyyy").format(Date(session.endTime.toEpochMilli() - 86400000))
                    ))
                }
            }
            Log.d("HEALTH", stagesResponse.records.toString() )
            val sleepSegmentEntity = SleepSegmentEntity(
                    startTime = session.startTime.toEpochMilli(),
                    endTime = session.endTime.toEpochMilli(),
                    duration = session.endTime.toEpochMilli() - session.startTime.toEpochMilli(),
                    date = SimpleDateFormat("dd/MM/yyyy").format(Date(session.endTime.toEpochMilli() - 86400000)),
                    status = 0,
                    rating = 0f,
                    alreadyRated = false
            )
            sleepSegmentEntity.sleepStages = sleepStages
            sessions.add(sleepSegmentEntity)
        }
        sleepRepository.saveAllSleepSegments(sessions)
    }
}