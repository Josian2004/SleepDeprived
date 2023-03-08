package fhict.sm.sleepdeprived.ui.startscreen

data class StartUiState (
    val timeAsleep: String = "No Data",
    val amountDrinks: Int = 0,

    val rateSleepSliderPosition: Float = 0f,
    val rateSleepSliderEnabled: Boolean = true
)
{

}