package fhict.sm.sleepdeprived.data.sleep.db

import androidx.room.*

@Dao
interface SleepSegmentDao {
    @Transaction
    @Query("SELECT * FROM sleep_segment_table ORDER BY start_time DESC")
    suspend fun getAll(): List<SleepSegmentWithStages>
    @Transaction
    @Query("SELECT * FROM sleep_segment_table ORDER BY start_time DESC LIMIT 2")
    suspend fun getLast2(): List<SleepSegmentWithStages>
    @Transaction
    @Query("SELECT * FROM sleep_segment_table WHERE date = :date")
    suspend fun getByNight(date: String): SleepSegmentWithStages?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sleepSegmentEventEntity: SleepSegmentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sleepSegmentEventEntities: List<SleepSegmentEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllStages(sleepStageEntities: List<SleepStageEntity>)

    @Query("DELETE FROM sleep_stage_table WHERE sleep_segment = :date")
    suspend fun deleteAllStagesByDate(date: String)
}