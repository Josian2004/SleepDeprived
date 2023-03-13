package fhict.sm.sleepdeprived.data.sleep.db

import androidx.room.Embedded
import androidx.room.Relation

data class SleepSegmentWithStages (
    @Embedded val sleepSegmentEntity: SleepSegmentEntity,
    @Relation(
        parentColumn = "date",
        entityColumn = "sleep_segment"
    )
    val stages: List<SleepStageEntity>
) {

}