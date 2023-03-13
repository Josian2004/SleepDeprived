package fhict.sm.sleepdeprived.ui.infoscreen

import androidx.compose.ui.res.stringArrayResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fhict.sm.sleepdeprived.R
import fhict.sm.sleepdeprived.domain.AppStateService
import fhict.sm.sleepdeprived.domain.SleepReason
import fhict.sm.sleepdeprived.domain.SleepState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val appStateService: AppStateService
): ViewModel() {
    private val _uiState = MutableStateFlow(InfoUiState())
    val uiState: StateFlow<InfoUiState> = _uiState.asStateFlow()


    init {
        val allTips: ArrayList<TipModel> = generateTips()
        viewModelScope.launch {
            val sleepState: SleepState = appStateService.getSleepState()
            val sleepReasons: List<SleepReason> = appStateService.getSleepReasons()

            val recommendedTips: ArrayList<TipModel> = ArrayList()
            val otherTips: ArrayList<TipModel> = ArrayList()

            val tooMuchCaffeine: Boolean = sleepReasons.contains(SleepReason.EXTRA_CAFFEINE)

            allTips.forEach {tip ->
                if (sleepReasons.contains(tip.sleepReason)) {
                    recommendedTips.add(tip)
                } else {
                    otherTips.add(tip)
                }
            }
            val characterText: String
            if (sleepReasons.isNotEmpty()) {
                when (sleepReasons[0]) {
                    SleepReason.CAFFEINE -> characterText = "I've noticed that you drank some caffeine right before going to bed, this might impact the quality of your sleep"
                    SleepReason.TWO_HOURS_SCHEDULE -> characterText = "Last night you went more than 1 hour later to bed than you did the day before."
                    SleepReason.THREE_HOURS_SCHEDULE -> characterText = "Last night you went more than 2 hours later to bed than you did the day before."

                    else -> {
                        characterText = "I didn't see any indication your actions could have impacted your sleep, good job!"
                    }
                }
            } else {
                characterText = "I didn't see any indication your actions could have impacted your sleep, good job!"
            }

            _uiState.update { currentState ->
                currentState.copy(
                    recommendedTips = recommendedTips,
                    otherTips = otherTips,
                    tooMuchCaffeine = tooMuchCaffeine,
                    characterText = characterText
                )
            }
        }
    }

    private fun generateTips(): ArrayList<TipModel> {
        val allTips: ArrayList<TipModel> = ArrayList()

        val tip1 = TipModel(
            "How can you reduce noise in your bedroom during the night?",
            R.drawable.noise_bedroom_stock,
            SleepReason.NOISE
            )
        tip1.addTipParagraph(
            arrayOf<String> (
                "The approach you can take to reduce noise in your room depends on where the noise is coming from. If the noise is coming from inside your room, try and locate the noise so you can turn it off, or lower it. When the noise is coming from inside your house you could do the same thing, but if it's something you can’t lower or turn off you could try to soundproof your room or the location the noise is coming from.",
                "Some things you can do to soundproof your room or other rooms is filling them with things. These things can be bookshelves or rugs and carpets. If you want to go more extreme with it, you can always use sound dampening curtains, or acoustic foam. When the sound is coming from something like a machine you could use the acoustic foam by wrapping it around or near it so the noise will be dampened.",
                "There is also the chance that the noise is coming from outside. In some cases you can’t do much against this, besides soundproofing your room. If it is something you can control like neighbors making a lot of sound or people having a party, you could try and talk to them about it."
            )
        )
        allTips.add(tip1)

        val tip2 = TipModel(
            "What are the consequences of drinking caffeine before going to bed?",
            R.drawable.coffee_stock,
            SleepReason.CAFFEINE
        )
        tip2.addTipParagraph(
            arrayOf<String> (
                "Dose-response studies demonstrate that increasing doses of caffeine administered at or near bedtime are associated with significant sleep disturbance. So if possible you should try and not drink a caffeinated drink before going to bed. To see the best results you should not drink any caffeinated drinks 6 hours before going to bed."
            )
        )
        allTips.add(tip2)

        val tip3 = TipModel(
            "What is the ideal temperature of your bedroom for the perfect night?",
            R.drawable.temperature_stock,
            SleepReason.TEMPERATURE
        )
        tip3.addTipParagraph(
            arrayOf<String> (
                "The best temperature for sleep is approximately 65 degrees Fahrenheit (18.3 degrees Celsius). This may vary by a few degrees from person to person, but most doctors recommend keeping the thermostat set between 60 to 67 degrees Fahrenheit (15.6 to 19.4 degrees Celsius) for the most comfortable sleep. If your preference lies higher or lower than these numbers and you have trouble sleeping, you could try sleeping in these temperatures once and check the result, they may help you sleep better."
            )
        )
        allTips.add(tip3)

        val tip4 = TipModel(
            "Why an inconsistent sleep schedule could lead to tiredness",
            R.drawable.sleep_schedule_stock,
            SleepReason.TWO_HOURS_SCHEDULE
        )
        tip4.addTipParagraph(
            arrayOf<String> (
                "An inconsistent sleep schedule can have a lot of negative effects on you and your health. When not having a consistent sleep schedule your body does not know when it is time to sleep. This is one of the reasons you could be feeling tired or sleepy during the day. Not having a consistent sleep schedule can also put a person at higher risk for obesity, high cholesterol, hypertension, high blood sugar and other metabolic disorders."
            )
        )
        allTips.add(tip4)


        val tip5 = TipModel(
            "What is the impact of using your phone before going to bed?",
            R.drawable.phone_usage_stock,
            SleepReason.SCREEN
        )
        tip5.addTipParagraph(
            arrayOf<String> (
                "Using your phone before going to sleep could contribute to you having a worse night sleep Some of the consequences of using your phone before going to bed are a delayed bedtime, sleep loss, irregular sleep-wake patterns, poor sleep quality and increased tiredness during the day. By using your phone before going to bed you stimulate your brain and this often results in delaying REM sleep.",
                "Besides keeping you awake when trying to go to sleep, your phone also emits blue light that some studies have shown mimics daylight, which makes you more alert. Something that you can't use when trying to go to sleep. Being exposed to it can also throw off your body’s internal clock and circadian rhythm, which would make it harder for your body to go into sleep mode. Blue light is not only bad for your sleep but also your eyes, but sadly this application is only about sleeping so we won’t go further into that here."
            )
        )
        allTips.add(tip5)

        return allTips
    }

    fun openTooMuchCaffeinePopup() {
        val tipModel: TipModel = TipModel(
            "",
            R.drawable.caffeine_warning_stock,
            SleepReason.EXTRA_CAFFEINE
        )
        tipModel.addTipParagraph(
            arrayOf<String>(
                "Hey, I have noticed you consumed more than 35 cups of coffee today. That in itself is impressive, but did you know that a lethal amount of caffeine is about 180 mg/L, which has been determined by examining the blood of patients who died from overdose. When you consume ~100 mg of caffeine, it raises your blood caffeine levels by about 5 mg/L, meaning it'll take almost 40 cups of coffee to be lethal. So good luck with the last 5 cups, time to break that record!"
            )
        )
        _uiState.update {currentState ->
            currentState.copy(
                selectedTip = tipModel,
                showPopUp = true
            )
        }
    }

    fun openTipDetailPopup(selectedTip: TipModel) {
        _uiState.update {currentState ->
            currentState.copy(
                selectedTip = selectedTip,
                showPopUp = true
            )
        }
    }

    fun closeTipDetailPopup() {
        _uiState.update {currentState ->
            currentState.copy(
                selectedTip = null,
                showPopUp = false
            )
        }
    }
}