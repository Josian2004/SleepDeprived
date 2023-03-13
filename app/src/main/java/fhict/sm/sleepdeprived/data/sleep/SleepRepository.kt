package fhict.sm.sleepdeprived.data.sleep

import dagger.Module
import dagger.hilt.InstallIn
import fhict.sm.sleepdeprived.MainActivity
import fhict.sm.sleepdeprived.data.sleep.db.SleepSegmentDao
import fhict.sm.sleepdeprived.data.sleep.db.SleepSegmentEntity
import fhict.sm.sleepdeprived.data.sleep.db.SleepSegmentWithStages
import fhict.sm.sleepdeprived.data.sleep.db.SleepStageEntity
import javax.inject.Inject

class SleepRepository @Inject constructor(
    private val sleepSegmentDao: SleepSegmentDao
)
{
    suspend fun saveSleepSegment(sleepSegmentEntity: SleepSegmentEntity) {
        sleepSegmentDao.deleteAllStagesByDate(sleepSegmentEntity.date)
        sleepSegmentDao.insert(sleepSegmentEntity)
        sleepSegmentDao.insertAllStages(sleepSegmentEntity.sleepStages)
    }

    suspend fun saveAllSleepSegments(sleepSegmentEntities: MutableList<SleepSegmentEntity>) {
        sleepSegmentDao.insertAll(sleepSegmentEntities)
        sleepSegmentEntities.forEach { sleepSegmentEntity ->
            sleepSegmentDao.deleteAllStagesByDate(sleepSegmentEntity.date)
            sleepSegmentDao.insertAllStages(sleepSegmentEntity.sleepStages)
        }
    }

    suspend fun getAllSleepSegments(): List<SleepSegmentEntity> {
        val sleepSegmentsWithStages: List<SleepSegmentWithStages> = sleepSegmentDao.getAll()
        val sleepSegments: ArrayList<SleepSegmentEntity> = ArrayList()
        sleepSegmentsWithStages.forEach { sleepSegmentWithStages ->
            val sleepSegmentEntity = sleepSegmentWithStages.sleepSegmentEntity
            sleepSegmentEntity.sleepStages = sleepSegmentWithStages.stages as ArrayList<SleepStageEntity>
            sleepSegments.add(sleepSegmentEntity)
        }

        return sleepSegments
    }

    suspend fun getSleepSegmentByNight(nightDate: String): SleepSegmentEntity? {
        val sleepSegmentWithStages = sleepSegmentDao.getByNight(nightDate)
        val sleepSegment = sleepSegmentWithStages?.sleepSegmentEntity
        sleepSegment?.sleepStages = sleepSegmentWithStages?.stages as ArrayList<SleepStageEntity>
        return sleepSegment
    }

    suspend fun getLast2SleepSegments(): List<SleepSegmentEntity> {
        val sleepSegmentsWithStages: List<SleepSegmentWithStages> = sleepSegmentDao.getLast2()
        val sleepSegments: ArrayList<SleepSegmentEntity> = ArrayList()
        sleepSegmentsWithStages.forEach { sleepSegmentWithStages ->
            val sleepSegmentEntity = sleepSegmentWithStages.sleepSegmentEntity
            sleepSegmentEntity.sleepStages = sleepSegmentWithStages.stages as ArrayList<SleepStageEntity>
            sleepSegments.add(sleepSegmentEntity)
        }

        return sleepSegments
    }
}