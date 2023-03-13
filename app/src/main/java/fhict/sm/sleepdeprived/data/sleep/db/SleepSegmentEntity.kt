package fhict.sm.sleepdeprived.data.sleep.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity(tableName = "sleep_segment_table")
data class SleepSegmentEntity(
    @PrimaryKey
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "start_time") val startTime: Long,
    @ColumnInfo(name = "end_time") val endTime: Long,
    @ColumnInfo(name = "duration") val duration: Long,
    @ColumnInfo(name = "status") val status: Int,

    @ColumnInfo(name = "rating") var rating: Float,
    @ColumnInfo(name = "already_rated") var alreadyRated: Boolean,

        )
{
    @Ignore
    lateinit var sleepStages: ArrayList<SleepStageEntity>

}