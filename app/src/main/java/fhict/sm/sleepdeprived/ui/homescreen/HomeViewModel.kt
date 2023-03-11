package fhict.sm.sleepdeprived.ui.homescreen

import android.util.Log
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.SleepStageRecord
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fhict.sm.sleepdeprived.data.caffeine.CaffeineRepository
import fhict.sm.sleepdeprived.data.caffeine.db.CaffeineEntity
import fhict.sm.sleepdeprived.data.sleep.SleepRepository
import fhict.sm.sleepdeprived.data.sleep.db.SleepSegmentEntity
import fhict.sm.sleepdeprived.data.sleep.db.SleepStageEntity
import fhict.sm.sleepdeprived.data.sleep.db.StageType
import fhict.sm.sleepdeprived.domain.AppStateService
import fhict.sm.sleepdeprived.domain.HealthConnectService
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
    private val appStateService: AppStateService,
    private val healthConnectService: HealthConnectService
    ): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    val permissionLauncher = healthConnectService.requestPermissionsActivityContract()
    private var currentSelectedNight: SleepSegmentEntity? = null

    init {
        viewModelScope.launch {
            if (healthConnectService.hasAllPermissions()) {
                init()
            }

        }
    }

    fun init() {
        _uiState.update { currentState ->
            currentState.copy(
                permissionsGranted = true
            )
        }
        viewModelScope.launch {
            healthConnectService.getSleepSessionData()
            val sleepSegments = sleepRepository.getAllSleepSegments()
            if (sleepSegments.isNotEmpty()) {
                if (sleepSegments[0].date.equals(SimpleDateFormat("dd/MM/yyyy").format(Date(System.currentTimeMillis() - 86400000)))) {
                    //currentSelectedNight = sleepSegments[0]
                    changeDay(sleepSegments[0])
                }
            } else {
                changeDay(null)
            }
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

    fun addDrink(): Unit {
        viewModelScope.launch {
            caffeineRepository.saveCaffeine(CaffeineEntity(System.currentTimeMillis()))

            val night = currentSelectedNight
            val caffeineEntities: List<CaffeineEntity>
            val timeSinceLastDrink: String
            val amountDrinks: Int

            if (night != null) {
                caffeineEntities = caffeineRepository.getAllCaffeineBetweenTime(night.endTime, System.currentTimeMillis() + 10)

                timeSinceLastDrink = if (caffeineEntities.isNotEmpty()) {
                    val unixTimeSinceLastDrink = System.currentTimeMillis() - caffeineEntities[0].intakeMoment
                    String.format("%02dh %02dm", TimeUnit.MILLISECONDS.toHours(unixTimeSinceLastDrink), TimeUnit.MILLISECONDS.toMinutes(unixTimeSinceLastDrink) % TimeUnit.HOURS.toMinutes(1))
                } else {
                    "Yesterday"
                }
                amountDrinks = caffeineRepository.getAllCaffeineBetweenTime(night.endTime, System.currentTimeMillis() + 10).size
            } else {
                caffeineEntities = caffeineRepository.getAllCaffeineBetweenTime(System.currentTimeMillis() - 86400000, System.currentTimeMillis() + 10)

                timeSinceLastDrink = if (!caffeineEntities.isEmpty()) {
                    val unixTimeSinceLastDrink = System.currentTimeMillis() - caffeineEntities[0].intakeMoment
                    String.format("%02dh %02dm", TimeUnit.MILLISECONDS.toHours(unixTimeSinceLastDrink), TimeUnit.MILLISECONDS.toMinutes(unixTimeSinceLastDrink) % TimeUnit.HOURS.toMinutes(1))
                } else {
                    "Yesterday"
                }
                amountDrinks = caffeineRepository.getAllCaffeineBetweenTime(System.currentTimeMillis() - 86400000, System.currentTimeMillis() + 10).size
            }

            _uiState.update { currentState ->
                currentState.copy(
                    amountDrinks = amountDrinks,
                    timeLastDrink = timeSinceLastDrink
                )
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
        var caffeineEntities: List<CaffeineEntity>
        var timeSinceLastDrink: String
        var timeAsleep: String
        var fromTil: String
        var sleepStages: List<SleepStageEntity>
        var sleepStagesDuration: Long

        Log.d("SLEEP", currentSelectedNight.toString())
        viewModelScope.launch {
            if (currentSelectedNight == null) {
                caffeineEntities = caffeineRepository.getAllCaffeineBetweenTime(System.currentTimeMillis() - 86400000, System.currentTimeMillis() + 10)
                timeAsleep = "No Data"
                fromTil = "No Data"
                sleepStages = emptyList()
                sleepStagesDuration = 0

            } else {
                Log.d("SLEEP", currentSelectedNight.toString())
                caffeineEntities = caffeineRepository.getAllCaffeineBetweenTime(currentSelectedNight!!.endTime, System.currentTimeMillis() + 10)
                timeAsleep =  String.format("%02dh %02dm", TimeUnit.MILLISECONDS.toHours(currentSelectedNight!!.duration), TimeUnit.MILLISECONDS.toMinutes(currentSelectedNight!!.duration) % TimeUnit.HOURS.toMinutes(1))
                fromTil = "${SimpleDateFormat("HH:mm").format(Date(currentSelectedNight!!.startTime))}-${SimpleDateFormat("HH:mm").format(Date(currentSelectedNight!!.endTime))}"
                sleepStages = currentSelectedNight!!.sleepStages
                sleepStagesDuration = currentSelectedNight!!.duration
            }
            timeSinceLastDrink = if (caffeineEntities.isNotEmpty()) {
                val unixTimeSinceLastDrink = System.currentTimeMillis() - caffeineEntities[0].intakeMoment
                String.format("%02dh %02dm", TimeUnit.MILLISECONDS.toHours(unixTimeSinceLastDrink), TimeUnit.MILLISECONDS.toMinutes(unixTimeSinceLastDrink) % TimeUnit.HOURS.toMinutes(1))
            } else {
                "Yesterday"
            }
            _uiState.update { currentState ->
                currentState.copy(
                    timeAsleep = timeAsleep,
                    fromTil = fromTil,
                    rateSleepSliderPosition = currentSelectedNight!!.rating,
                    amountDrinks = caffeineEntities.size,
                    timeLastDrink = timeSinceLastDrink,
                    selectedNight = currentSelectedNight!!.date,
                    sleepStages = sleepStages,
                    sleepStagesDuration = sleepStagesDuration
                )
            }

        }
        }
    }