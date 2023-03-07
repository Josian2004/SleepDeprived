package fhict.sm.sleepdeprived.ui.homescreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fhict.sm.sleepdeprived.data.sleep.SleepRepository
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
class HomeViewModel @Inject constructor(private val sleepRepository: SleepRepository): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    private lateinit var currentSelectedNight: String

    init {
        viewModelScope.launch {
            val sleepSegments = sleepRepository.getAllSleepSegments()
            /*Log.d(
                "SLEEP DATABASE",
                "${sleepSegments[0]}"
            )*/

            Log.d(
                "SLEEP DATABASE",
                "$sleepSegments"
            )


            if (sleepSegments.isEmpty() or !sleepSegments[0].date.equals(SimpleDateFormat("dd/MM/yyyy").format(Date(System.currentTimeMillis() - 86400000)))) {
                currentSelectedNight = SimpleDateFormat("dd/MM/yyyy").format(Date(System.currentTimeMillis() - 86400000))
                _uiState.update { currentState ->
                    currentState.copy(
                        timeAsleep = "No Data",
                        fromTil = "No Data",
                        rateSleepSliderEnabled = false
                    )
                }
            } else {
                currentSelectedNight = sleepSegments[0].date
                val timeAsleep =  String.format("%02dh %02dm", TimeUnit.MILLISECONDS.toHours(sleepSegments[0].duration), TimeUnit.MILLISECONDS.toMinutes(sleepSegments[0].duration) % TimeUnit.HOURS.toMinutes(1))
                val fromTil = "${SimpleDateFormat("HH:mm").format(Date(sleepSegments[0].startTime))}-${SimpleDateFormat("HH:mm").format(Date(sleepSegments[0].endTime))}"
                _uiState.update { currentState ->
                    currentState.copy(
                        timeAsleep = timeAsleep,
                        fromTil = fromTil,
                        rateSleepSliderPosition = sleepSegments[0].rating
                    )
                }
            }
        }
    }

    fun changeRateSleepSliderPos(sliderPos: Float): Unit {
        viewModelScope.launch {
            val night = sleepRepository.getSleepSegmentByNight(currentSelectedNight)
            if (night != null) {
                night.rating = sliderPos
                night.alreadyRated = true
                _uiState.update { currentState ->
                    currentState.copy(rateSleepSliderPosition = sliderPos)
                }
                sleepRepository.saveSleepSegment(night)
            }
        }
    }

    fun addDrink(currentAmount: Int): Unit {
        sleepRepository.toString()
        _uiState.update { currentState ->
            currentState.copy(amountDrinks = currentAmount + 1)
        }


    }
}