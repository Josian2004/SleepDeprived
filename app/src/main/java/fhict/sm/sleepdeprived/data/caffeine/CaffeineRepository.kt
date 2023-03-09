package fhict.sm.sleepdeprived.data.caffeine

import fhict.sm.sleepdeprived.data.caffeine.db.CaffeineDao
import fhict.sm.sleepdeprived.data.caffeine.db.CaffeineEntity
import javax.inject.Inject

class CaffeineRepository @Inject constructor(
    private val caffeineDao: CaffeineDao
) {
    suspend fun saveCaffeine(caffeineEntity: CaffeineEntity) {
        caffeineDao.insert(caffeineEntity)
    }

    suspend fun getAllCaffeineBetweenTime(start: Long, end: Long): List<CaffeineEntity> {
        return caffeineDao.getAllByStartAndEnd(start, end)
    }

    suspend fun getLastCaffeine(): CaffeineEntity {
        return caffeineDao.getLastCaffeine()
    }
}