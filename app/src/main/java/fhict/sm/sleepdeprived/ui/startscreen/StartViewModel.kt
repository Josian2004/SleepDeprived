package fhict.sm.sleepdeprived.ui.startscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fhict.sm.sleepdeprived.data.caffeine.CaffeineRepository
import fhict.sm.sleepdeprived.data.sleep.SleepRepository
import fhict.sm.sleepdeprived.data.sleep.db.SleepSegmentEntity
import fhict.sm.sleepdeprived.domain.AppStateService
import fhict.sm.sleepdeprived.domain.SleepReason
import fhict.sm.sleepdeprived.domain.SleepState
import fhict.sm.sleepdeprived.ui.homescreen.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val sleepRepository: SleepRepository,
    private val caffeineRepository: CaffeineRepository,
    private val appStateService: AppStateService
): ViewModel() {
    private val _uiState = MutableStateFlow(StartUiState())
    val uiState: StateFlow<StartUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            /*val sleepSegments = sleepRepository.getAllSleepSegments()
            if (sleepSegments.isNotEmpty()) {
                val sleepSegment: SleepSegmentEntity = sleepSegments[0]
                if (sleepSegment.date.equals(SimpleDateFormat("dd/MM/yyyy").format(Date(System.currentTimeMillis() - 86400000)))) {
                    val timeAsleep =  String.format("%02d hours and %02d minutes", TimeUnit.MILLISECONDS.toHours(sleepSegment.duration), TimeUnit.MILLISECONDS.toMinutes(sleepSegment.duration) % TimeUnit.HOURS.toMinutes(1))

                    _uiState.update { currentState ->
                        currentState.copy(
                            timeAsleep = timeAsleep
                        )
                    }
                }
            }*/

            val sleepState: SleepState = appStateService.getSleepState()
            val sleepReasons: List<SleepReason> = appStateService.getSleepReasons()
            val nightSummaryTitle: String = generateNightSummaryTitle(sleepState)
            val nightSummaryParagraph: String = generateNightSummaryParagraph(sleepState, sleepReasons)

            _uiState.update { currentState ->
                currentState.copy(
                    nightSummaryTitle = nightSummaryTitle,
                    nightSummaryParagraph = nightSummaryParagraph
                )
            }
        }
    }


    private fun generateNightSummaryTitle(sleepState: SleepState): String {
        when(sleepState) {
            SleepState.NO_DATA -> {
                return "The app was unable to track your sleep last night."
            }
            SleepState.CRITICAL -> {
                return "You have slept far too little last night!"
            }
            SleepState.BELOW_REC -> {
                return "You slept below the recommended amount last night."
            }
            SleepState.LESS_THEN_GOAL -> {
                return "You slept enough last night, unfortunately you didn't reach your goal."
            }
            SleepState.GOOD -> {
                return "You slept enough and reached your goal, good job!"
            }
            SleepState.OVER -> {
                return "You slept a little bit too much last night."
            }
        }
    }

    private suspend fun generateNightSummaryParagraph(sleepState: SleepState, sleepReasons: List<SleepReason>): String {
        var paragraph: String = ""
        if((sleepState == SleepState.NO_DATA) or (sleepReasons[0] == SleepReason.NO_DATA)) {
            return "For some reason was the app unable to track your sleep last night, make sure you have your phone turned on for the whole night."
        }
        val lastNight: SleepSegmentEntity = sleepRepository.getLast2SleepSegments()[0]
        val timeAsleep: String = String.format("%02d Hours and %02d Minutes", TimeUnit.MILLISECONDS.toHours(lastNight.duration), TimeUnit.MILLISECONDS.toMinutes(lastNight.duration) % TimeUnit.HOURS.toMinutes(1))

        when(sleepState) {
            SleepState.CRITICAL -> {
                paragraph += "Last night you slept for $timeAsleep, that is a dangerously low amount of sleep. Try to go to bed a few hours earlier and look at some tips!"
            }
            SleepState.BELOW_REC -> {
                paragraph += "Last night you slept for $timeAsleep, that isn't enough sleep for you. We recommend you sleep least 7 hours per night, try to go to bed a little bit earlier and look at some tips!"
            }
            SleepState.LESS_THEN_GOAL -> {
                paragraph += "Last night you slept for $timeAsleep, that is enough sleep for you but you unfortunately didn't reach your sleep goal. Maybe use some of our tips to reach your goal tonight!"
            }
            SleepState.GOOD -> {
                paragraph += "Last night you slept for $timeAsleep, that is a good amount of sleep and you reached your sleep goal! Good job and keep it up!"
            }
            SleepState.OVER -> {
                paragraph += "Last night you slept for $timeAsleep, this is a little but too much sleep in a night which could have bad consequences. Look at some tips on how to fix oversleeping."
            }
        }


        sleepReasons.forEach { sleepReason ->
            paragraph += "\n" + "\n"
            when(sleepReason) {
                SleepReason.CAFFEINE -> {
                    paragraph += " I've noticed that you drank some caffeine before going to bed, caffeine keeps you awake and could lead to having trouble falling asleep."
                }
                SleepReason.NO_CAFFEINE -> {
                    paragraph += " You didn't drink any caffeine in the hour before going to bed, good job!"
                }
                SleepReason.SCREEN -> {
                    paragraph += " You seem to be using your phone screen a lot before going to sleep, the blue light from your phone could keep you awake."
                }
                SleepReason.NO_SCREEN -> {
                    paragraph += " You don't seem yo be using your phone before going to bed, good job!"
                }
                SleepReason.SAME_SCHEDULE -> {
                    paragraph += " It's important to keep to a consistent sleep schedule and you have achieved that."
                }
                SleepReason.TWO_HOURS_SCHEDULE -> {
                    paragraph += " It's important to keep to a consistent sleep schedule but your schedule fluctuates a little bit from two nights ago."
                }
                SleepReason.THREE_HOURS_SCHEDULE -> {
                    paragraph += " It's important to keep to a consistent sleep schedule but your schedule fluctuates a lot from two nights ago, try to find a more consistent schedule."
                }
            }
        }

        return paragraph
    }

}