package fhict.sm.sleepdeprived.ui.homescreen

import androidx.health.connect.client.records.SleepStageRecord
import fhict.sm.sleepdeprived.data.sleep.db.SleepStageEntity

data class HomeUiState (
    val timeAsleep: String = "No Data",
    val fromTil: String = "No Data",
    val amountDrinks: Int = 0,
    val timeLastDrink: String = "No Data",
    val sleepStages: List<SleepStageEntity> = emptyList(),
    val sleepStagesDuration: Long = 0,

    val rateSleepSliderPosition: Float = 0f,
    val rateSleepSliderEnabled: Boolean = true,

    val historyList: ArrayList<HistoryModel> = ArrayList(),
    val selectedNight: String = "No Data",

    val permissionsGranted: Boolean = false
        ) {


}