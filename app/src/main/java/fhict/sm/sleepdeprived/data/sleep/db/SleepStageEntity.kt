package fhict.sm.sleepdeprived.data.sleep.db

import androidx.health.connect.client.records.SleepStageRecord
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

enum class StageType {
    UNKNOWN,
    AWAKE,
    SLEEPING,
    OUT_OF_BED,
    LIGHT,
    DEEP,
    REM
}

@Entity("sleep_stage_table")
data class SleepStageEntity(
    @ColumnInfo(name = "start_time") val startTime: Long,
    @ColumnInfo(name = "end_time") val endTime: Long,
    @ColumnInfo(name = "duration") val duration: Long,
    @ColumnInfo(name = "status") val type: StageType,
    @ColumnInfo(name = "sleep_segment") val sleepSegment: String
) {
    @PrimaryKey
    @ColumnInfo(name = "id") var id: UUID = UUID.randomUUID()
}