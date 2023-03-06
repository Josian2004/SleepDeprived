package fhict.sm.sleepdeprived.homescreen

data class HomeUiState (
    val timeAsleep: String = "8h 12m",
    val fromTil: String = "01:13-09:32",
    val amountDrinks: Int = 6,
    val timeLastDrink: String = "1h 58m",
    val rateSleepSliderPosition: Float = 0f
        )