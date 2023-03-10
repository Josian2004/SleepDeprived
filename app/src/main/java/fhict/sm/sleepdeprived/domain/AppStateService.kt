package fhict.sm.sleepdeprived.domain

import fhict.sm.sleepdeprived.data.caffeine.CaffeineRepository
import fhict.sm.sleepdeprived.data.sleep.SleepRepository
import fhict.sm.sleepdeprived.data.sleep.db.SleepSegmentEntity
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.math.abs

enum class SleepState {
    LESS_THEN_GOAL, BELOW_REC, CRITICAL, GOOD, OVER, NO_DATA
}


enum class SleepReason {
    SCREEN, CAFFEINE, EXTRA_CAFFEINE, TWO_HOURS_SCHEDULE, THREE_HOURS_SCHEDULE, NOISE, TEMPERATURE, NO_SCREEN, NO_CAFFEINE, SAME_SCHEDULE, NO_DATA
}

class AppStateService @Inject constructor(
    private val sleepRepository: SleepRepository,
    private val caffeineRepository: CaffeineRepository
) {

    suspend fun getSleepState(): SleepState {
        val last2SleepSegments = sleepRepository.getLast2SleepSegments()
        if (last2SleepSegments.isEmpty()) {
            return SleepState.NO_DATA
        }
        val night1 = last2SleepSegments[0]
        if (night1.date != SimpleDateFormat("dd/MM/yyyy").format(Date(System.currentTimeMillis() - 86400000))) {
            return SleepState.NO_DATA
        }

        val dur1 = night1.duration
        when  {
            dur1 < 14400000.toLong() -> return SleepState.CRITICAL
            (dur1 > 14400000.toLong()) and (dur1 < 25200000.toLong()) -> return SleepState.BELOW_REC
            (dur1 > 25200000.toLong()) and (dur1 < 28800000.toLong()) -> return SleepState.LESS_THEN_GOAL
            (dur1 > 28800000.toLong()) and (dur1 < 32400000.toLong()) -> return SleepState.GOOD
            dur1 > 32400000.toLong() -> return SleepState.OVER
        }
        return SleepState.NO_DATA
    }

    suspend fun getSleepReasons(): ArrayList<SleepReason> {
        val reasons: ArrayList<SleepReason> = ArrayList()

        val last2SleepSegments = sleepRepository.getLast2SleepSegments()
        if (last2SleepSegments.isEmpty()) {
            reasons.add(SleepReason.NO_DATA)
            return reasons
        }
        val night1 = last2SleepSegments[0]
        if (night1.date != SimpleDateFormat("dd/MM/yyyy").format(Date(System.currentTimeMillis() - 86400000))) {
            reasons.add(SleepReason.NO_DATA)
            return reasons
        }

        //Caffeine
        if (caffeineRepository.getAllCaffeineBetweenTime(night1.startTime - 3600000, night1.startTime).isNotEmpty()) {
            reasons.add(SleepReason.CAFFEINE)
        } else {
            reasons.add(SleepReason.NO_CAFFEINE)
        }
        if (caffeineRepository.getAllCaffeineBetweenTime(night1.endTime, System.currentTimeMillis()).size >= 35) {
            reasons.add(SleepReason.EXTRA_CAFFEINE)
        }


        if (last2SleepSegments.size == 2) {
            val night2 = last2SleepSegments[1]
            val differenceBetweenStart = abs((night1.startTime - 86400000) - night2.startTime)
            when {
                differenceBetweenStart < 3600000 -> reasons.add(SleepReason.SAME_SCHEDULE)
                (differenceBetweenStart > 3600000) and (differenceBetweenStart < 7200000) -> reasons.add(SleepReason.TWO_HOURS_SCHEDULE)
                differenceBetweenStart > 10800000 -> reasons.add(SleepReason.THREE_HOURS_SCHEDULE)
            }
        }


        return reasons
    }

}