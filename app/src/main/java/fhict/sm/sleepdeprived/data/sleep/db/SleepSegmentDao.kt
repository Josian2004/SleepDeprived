package fhict.sm.sleepdeprived.data.sleep.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SleepSegmentDao {
    @Query("SELECT * FROM sleep_segment_table ORDER BY start_time DESC")
    suspend fun getAll(): List<SleepSegmentEntity>

    @Query("SELECT * FROM sleep_segment_table WHERE date = :date")
    suspend fun getByNight(date: String): SleepSegmentEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sleepSegmentEventEntity: SleepSegmentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sleepSegmentEventEntities: List<SleepSegmentEntity>)
}