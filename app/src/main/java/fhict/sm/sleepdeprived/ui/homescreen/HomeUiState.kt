package fhict.sm.sleepdeprived.ui.homescreen

data class HomeUiState (
    val timeAsleep: String = "No Data",
    val fromTil: String = "No Data",
    val amountDrinks: Int = 6,
    val timeLastDrink: String = "1h 58m",

    val rateSleepSliderPosition: Float = 0f,
    val rateSleepSliderEnabled: Boolean = true,

    val historyList: ArrayList<HistoryModel> = ArrayList(),
    val selectedNight: String = "No Data"
        ) {


}