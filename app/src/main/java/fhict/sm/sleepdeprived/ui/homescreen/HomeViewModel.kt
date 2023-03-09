package fhict.sm.sleepdeprived.ui.homescreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fhict.sm.sleepdeprived.data.caffeine.CaffeineRepository
import fhict.sm.sleepdeprived.data.caffeine.db.CaffeineEntity
import fhict.sm.sleepdeprived.data.sleep.SleepRepository
import fhict.sm.sleepdeprived.data.sleep.db.SleepSegmentEntity
import fhict.sm.sleepdeprived.domain.AppStateService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.math.floor

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sleepRepository: SleepRepository,
    private val caffeineRepository: CaffeineRepository,
    private val appStateService: AppStateService
    ): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    private var currentSelectedNight: SleepSegmentEntity? = null

    init {
        viewModelScope.launch {
            val sleepSegments = sleepRepository.getAllSleepSegments()
            if (sleepSegments.isNotEmpty()) {
                if (sleepSegments[0].date.equals(SimpleDateFormat("dd/MM/yyyy").format(Date(System.currentTimeMillis() - 86400000)))) {
                    //currentSelectedNight = sleepSegments[0]
                    changeDay(sleepSegments[0])
                }
            } else {
                changeDay(null)
            }
            /*Log.d(
                "SLEEP DATABASE",
                "${sleepSegments[0]}"
            )*/

            Log.d(
                "SLEEP DATABASE",
                "$sleepSegments"
            )


            /*if (sleepSegments.isEmpty() or !sleepSegments[0].date.equals(SimpleDateFormat("dd/MM/yyyy").format(Date(System.currentTimeMillis() - 86400000)))) {
                currentSelectedNight = SimpleDateFormat("dd/MM/yyyy").format(Date(System.currentTimeMillis() - 86400000))

                val caffeineEntities = caffeineRepository.getAllCaffeineBetweenTime(System.currentTimeMillis() - 86400000, System.currentTimeMillis() + 10)

                val timeSinceLastDrink: String = if (!caffeineEntities.isEmpty()) {
                    val unixTimeSinceLastDrink = System.currentTimeMillis() - caffeineEntities[0].intakeMoment
                    String.format("%02dh %02dm", TimeUnit.MILLISECONDS.toHours(unixTimeSinceLastDrink), TimeUnit.MILLISECONDS.toMinutes(unixTimeSinceLastDrink) % TimeUnit.HOURS.toMinutes(1))
                } else {
                    "Yesterday"
                }

                _uiState.update { currentState ->
                    currentState.copy(
                        timeAsleep = "No Data",
                        fromTil = "No Data",
                        rateSleepSliderEnabled = false,
                        amountDrinks = caffeineEntities.size,
                        timeLastDrink = timeSinceLastDrink
                    )
                }
            } else {
                currentSelectedNight = sleepSegments[0].date

                val caffeineEntities = caffeineRepository.getAllCaffeineBetweenTime(sleepSegments[0].endTime, System.currentTimeMillis() + 10)

                val timeSinceLastDrink: String = if (!caffeineEntities.isEmpty()) {
                    val unixTimeSinceLastDrink = System.currentTimeMillis() - caffeineEntities[0].intakeMoment
                    String.format("%02dh %02dm", TimeUnit.MILLISECONDS.toHours(unixTimeSinceLastDrink), TimeUnit.MILLISECONDS.toMinutes(unixTimeSinceLastDrink) % TimeUnit.HOURS.toMinutes(1))
                } else {
                    "Yesterday"
                }

                val timeAsleep =  String.format("%02dh %02dm", TimeUnit.MILLISECONDS.toHours(sleepSegments[0].duration), TimeUnit.MILLISECONDS.toMinutes(sleepSegments[0].duration) % TimeUnit.HOURS.toMinutes(1))
                val fromTil = "${SimpleDateFormat("HH:mm").format(Date(sleepSegments[0].startTime))}-${SimpleDateFormat("HH:mm").format(Date(sleepSegments[0].endTime))}"

                _uiState.update { currentState ->
                    currentState.copy(
                        timeAsleep = timeAsleep,
                        fromTil = fromTil,
                        rateSleepSliderPosition = sleepSegments[0].rating,
                        amountDrinks = caffeineEntities.size,
                        timeLastDrink = timeSinceLastDrink
                    )
                }
            }*/
            populateHistory()
        }
    }

    private fun populateHistory() {
        viewModelScope.launch {
            val sleepSegments: List<SleepSegmentEntity> = sleepRepository.getAllSleepSegments()
            val historyModels: ArrayList<HistoryModel> = ArrayList()

            if(sleepSegments.isNotEmpty()) {
                sleepSegments.forEach { sleepSegment ->
                    historyModels.add(HistoryModel(sleepSegment.date, floor(TimeUnit.MILLISECONDS.toHours(sleepSegment.duration).toFloat())))
                }
                historyModels.reverse()
                _uiState.update { currentState ->
                    currentState.copy(
                        historyList = historyModels
                    )
                }
            }
        }
    }

    fun changeRateSleepSliderPos(sliderPos: Float): Unit {
        viewModelScope.launch {
            val night = currentSelectedNight
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

    fun test() {
        viewModelScope.launch {
            sleepRepository.saveSleepSegment(SleepSegmentEntity("07/03/2023", 1678234440000, 1678261200000, 26760000, 0, 0f, false))
        }
    }

    fun addDrink(): Unit {
        viewModelScope.launch {
            caffeineRepository.saveCaffeine(CaffeineEntity(System.currentTimeMillis()))

            val night = currentSelectedNight

            if (night != null) {

                val caffeineEntities = caffeineRepository.getAllCaffeineBetweenTime(night.endTime, System.currentTimeMillis() + 10)

                val timeSinceLastDrink: String = if (!caffeineEntities.isEmpty()) {
                    val unixTimeSinceLastDrink = System.currentTimeMillis() - caffeineEntities[0].intakeMoment
                    String.format("%02dh %02dm", TimeUnit.MILLISECONDS.toHours(unixTimeSinceLastDrink), TimeUnit.MILLISECONDS.toMinutes(unixTimeSinceLastDrink) % TimeUnit.HOURS.toMinutes(1))
                } else {
                    "Yesterday"
                }

                _uiState.update { currentState ->
                    currentState.copy(
                        amountDrinks = caffeineRepository.getAllCaffeineBetweenTime(night.endTime, System.currentTimeMillis() + 10).size,
                        timeLastDrink = timeSinceLastDrink
                    )
                }
            } else {

                val caffeineEntities = caffeineRepository.getAllCaffeineBetweenTime(System.currentTimeMillis() - 86400000, System.currentTimeMillis() + 10)

                val timeSinceLastDrink: String = if (!caffeineEntities.isEmpty()) {
                    val unixTimeSinceLastDrink = System.currentTimeMillis() - caffeineEntities[0].intakeMoment
                    String.format("%02dh %02dm", TimeUnit.MILLISECONDS.toHours(unixTimeSinceLastDrink), TimeUnit.MILLISECONDS.toMinutes(unixTimeSinceLastDrink) % TimeUnit.HOURS.toMinutes(1))
                } else {
                    "Yesterday"
                }


                _uiState.update { currentState ->
                    currentState.copy(
                        amountDrinks = caffeineRepository.getAllCaffeineBetweenTime(System.currentTimeMillis() - 86400000, System.currentTimeMillis() + 10).size,
                        timeLastDrink = timeSinceLastDrink
                    )
                }
            }
        }


    }

    fun historyPressed(stringDay: String) {
        viewModelScope.launch {
            changeDay(sleepRepository.getSleepSegmentByNight(stringDay))
        }
    }

    private fun changeDay(day: SleepSegmentEntity?)
    {
        currentSelectedNight = day
        Log.d("SLEEP", currentSelectedNight.toString())
        viewModelScope.launch {
            if (currentSelectedNight == null) {
                //currentSelectedNight = SimpleDateFormat("dd/MM/yyyy").format(Date(System.currentTimeMillis() - 86400000))

                val caffeineEntities = caffeineRepository.getAllCaffeineBetweenTime(System.currentTimeMillis() - 86400000, System.currentTimeMillis() + 10)

                val timeSinceLastDrink: String = if (caffeineEntities.isNotEmpty()) {
                    val unixTimeSinceLastDrink = System.currentTimeMillis() - caffeineEntities[0].intakeMoment
                    String.format("%02dh %02dm", TimeUnit.MILLISECONDS.toHours(unixTimeSinceLastDrink), TimeUnit.MILLISECONDS.toMinutes(unixTimeSinceLastDrink) % TimeUnit.HOURS.toMinutes(1))
                } else {
                    "Yesterday"
                }

                _uiState.update { currentState ->
                    currentState.copy(
                        timeAsleep = "No Data",
                        fromTil = "No Data",
                        rateSleepSliderEnabled = false,
                        amountDrinks = caffeineEntities.size,
                        timeLastDrink = timeSinceLastDrink
                    )
                }
            } else {
                //currentSelectedNight = sleepSegments[0].date
                Log.d("SLEEP", currentSelectedNight.toString())
                val caffeineEntities = caffeineRepository.getAllCaffeineBetweenTime(currentSelectedNight!!.endTime, System.currentTimeMillis() + 10)

                val timeSinceLastDrink: String = if (caffeineEntities.isNotEmpty()) {
                    val unixTimeSinceLastDrink = System.currentTimeMillis() - caffeineEntities[0].intakeMoment
                    String.format("%02dh %02dm", TimeUnit.MILLISECONDS.toHours(unixTimeSinceLastDrink), TimeUnit.MILLISECONDS.toMinutes(unixTimeSinceLastDrink) % TimeUnit.HOURS.toMinutes(1))
                } else {
                    "Yesterday"
                }

                val timeAsleep =  String.format("%02dh %02dm", TimeUnit.MILLISECONDS.toHours(currentSelectedNight!!.duration), TimeUnit.MILLISECONDS.toMinutes(currentSelectedNight!!.duration) % TimeUnit.HOURS.toMinutes(1))
                val fromTil = "${SimpleDateFormat("HH:mm").format(Date(currentSelectedNight!!.startTime))}-${SimpleDateFormat("HH:mm").format(Date(currentSelectedNight!!.endTime))}"

                _uiState.update { currentState ->
                    currentState.copy(
                        timeAsleep = timeAsleep,
                        fromTil = fromTil,
                        rateSleepSliderPosition = currentSelectedNight!!.rating,
                        amountDrinks = caffeineEntities.size,
                        timeLastDrink = timeSinceLastDrink,
                        selectedNight = currentSelectedNight!!.date
                    )
                }
        }

        }
        }
    }