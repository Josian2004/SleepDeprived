package fhict.sm.sleepdeprived.data

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fhict.sm.sleepdeprived.data.sleep.db.SleepSegmentDao
import fhict.sm.sleepdeprived.data.sleep.db.SleepSegmentEntity


/*@Database(
    entities = [SleepSegmentEntity::class],
    version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sleepSegmentDao(): SleepSegmentDao
}*/

@Database(
    entities = [SleepSegmentEntity::class],
    version = 1,
    exportSchema = true,
    /*autoMigrations = [
        AutoMigration (from = 1, to = 2)
    ]*/
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sleepSegmentDao(): SleepSegmentDao
}