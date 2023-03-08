package fhict.sm.sleepdeprived.ui.startscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fhict.sm.sleepdeprived.data.caffeine.CaffeineRepository
import fhict.sm.sleepdeprived.data.sleep.SleepRepository
import fhict.sm.sleepdeprived.data.sleep.db.SleepSegmentEntity
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
    private val caffeineRepository: CaffeineRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(StartUiState())
    val uiState: StateFlow<StartUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val sleepSegment: SleepSegmentEntity = sleepRepository.getAllSleepSegments()[0]
            if (sleepSegment.date.equals(SimpleDateFormat("dd/MM/yyyy").format(Date(System.currentTimeMillis() - 86400000)))) {
                val timeAsleep =  String.format("%02d hours and %02d minutes", TimeUnit.MILLISECONDS.toHours(sleepSegment.duration), TimeUnit.MILLISECONDS.toMinutes(sleepSegment.duration) % TimeUnit.HOURS.toMinutes(1))

                _uiState.update { currentState ->
                    currentState.copy(
                        timeAsleep = timeAsleep
                    )
                }
            }
        }
    }

}