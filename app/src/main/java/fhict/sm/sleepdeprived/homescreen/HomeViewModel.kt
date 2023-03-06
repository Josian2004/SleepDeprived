package fhict.sm.sleepdeprived.homescreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun changeRateSleepSliderPos(sliderPos: Float): Unit {
        _uiState.update { currentState ->
            currentState.copy(rateSleepSliderPosition = sliderPos)
        }
    }

    fun addDrink(currentAmount: Int): Unit {
        _uiState.update { currentState ->
            currentState.copy(amountDrinks = currentAmount + 1)
        }
    }
}