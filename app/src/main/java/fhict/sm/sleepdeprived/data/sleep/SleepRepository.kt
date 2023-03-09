package fhict.sm.sleepdeprived.data.sleep

import dagger.Module
import dagger.hilt.InstallIn
import fhict.sm.sleepdeprived.MainActivity
import fhict.sm.sleepdeprived.data.sleep.db.SleepSegmentDao
import fhict.sm.sleepdeprived.data.sleep.db.SleepSegmentEntity
import javax.inject.Inject

class SleepRepository @Inject constructor(
    private val sleepSegmentDao: SleepSegmentDao
)
{
    suspend fun saveSleepSegment(sleepSegmentEntity: SleepSegmentEntity) {
        sleepSegmentDao.insert(sleepSegmentEntity)
    }

    suspend fun getAllSleepSegments(): List<SleepSegmentEntity> {
        return sleepSegmentDao.getAll()
    }

    suspend fun getSleepSegmentByNight(nightDate: String): SleepSegmentEntity? {
        return sleepSegmentDao.getByNight(nightDate)
    }

    suspend fun getLast2SleepSegments(): List<SleepSegmentEntity> {
        return sleepSegmentDao.getLast2()
    }
}