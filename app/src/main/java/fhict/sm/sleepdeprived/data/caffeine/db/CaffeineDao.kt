package fhict.sm.sleepdeprived.data.caffeine.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CaffeineDao {

    @Query("SELECT * FROM caffeine_table WHERE intake_moment BETWEEN :start AND :end ORDER BY intake_moment DESC")
    suspend fun getAllByStartAndEnd(start: Long, end: Long): List<CaffeineEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(caffeineEntity: CaffeineEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(caffeineEntity: CaffeineEntity)

    @Query("SELECT * FROM caffeine_table ORDER BY intake_moment DESC LIMIT 1")
    suspend fun getLastCaffeine(): CaffeineEntity
}