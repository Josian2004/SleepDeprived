package fhict.sm.sleepdeprived.homescreen

data class HomeUiState (
    var timeAsleep: String = "8h 12m",
    var fromTil: String = "01:13-09:32",
    var amountDrinks: Int = 6,
    var timeLastDrink: String = "1h 58m",
    var rateSleepSliderPosition: Float = 0f
        )