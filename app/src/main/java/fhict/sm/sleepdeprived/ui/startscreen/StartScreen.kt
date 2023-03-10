package fhict.sm.sleepdeprived.ui.startscreen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import fhict.sm.sleepdeprived.R
import fhict.sm.sleepdeprived.ui.theme.aBeeZeeFamily

@Composable
fun StartScreen(
    startViewModel: StartViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .verticalScroll(enabled = true, state = rememberScrollState())
    ) {
        val startUiState by startViewModel.uiState.collectAsState()

        StartHeader()
        Character(startUiState)
        RateSleepSlider(startUiState)
        NightSummary(startUiState)
        Row() {
            Box(modifier = Modifier.weight(1f)) {
                AddCaffeine(startUiState)
            }
            Box(modifier = Modifier.weight(1f)) {
                GoalSummary(startUiState)
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )
    }
}

@Composable
fun StartHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Text(
            text = "Home",
            fontFamily = aBeeZeeFamily,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colors.onPrimary,
            fontSize = 32.sp,
            modifier = Modifier.padding(top = 25.dp, start = 20.dp)
        )
        Box(
            modifier = Modifier
                .padding(top = 10.dp, end = 20.dp)
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.Gray)

        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_picture),
                contentDescription = "Profile Picture"
            )
        }
    }
}

@Composable
fun Character(startUiState: StartUiState) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(bottom = 0.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.muts),
            contentDescription = "Character",
            modifier = Modifier
                .width(400.dp)
        )
    }
}

@Composable
fun RateSleepSlider(startUiState: StartUiState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, top = 15.dp)
            .clip(RoundedCornerShape(9.dp))
            .background(MaterialTheme.colors.primary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Good morning, how did you feel like you slept?",
            fontSize = 20.sp,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.padding(top = 10.dp, bottom = 0.dp, start = 10.dp, end = 10.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, bottom = 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "1",
                fontSize = 20.sp,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.padding(top = 12.dp, bottom = 0.dp, start = 0.dp, end = 10.dp)
            )
            Box(modifier = Modifier.weight(1f)) {
                //var sliderPosition by remember { mutableStateOf(0f) }
                Slider(
                    value = 0f /*sliderPosition*/,
                    onValueChange = {}/*{ changeSliderPos(it) }*/,
                    valueRange = 1f..10f,
                    steps = 8,

                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colors.onPrimary,
                        activeTickColor = MaterialTheme.colors.background,
                        inactiveTickColor = MaterialTheme.colors.background,
                        activeTrackColor = MaterialTheme.colors.onPrimary
                    )
                )
            }
            Text(
                "10",
                fontSize = 20.sp,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.padding(top = 12.dp, bottom = 0.dp, start = 0.dp, end = 10.dp)
            )
        }
    }
}

@Composable
fun NightSummary(startUiState: StartUiState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .clip(RoundedCornerShape(9.dp))
            .width(300.dp)
            .background(MaterialTheme.colors.primary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = startUiState.nightSummaryTitle,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = startUiState.nightSummaryParagraph,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
        )
    }
}

@Composable
fun GoalSummary(startUiState: StartUiState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 7.dp, end = 15.dp)
            .clip(RoundedCornerShape(9.dp))
            .background(MaterialTheme.colors.primary)
    ) {
        Text(
            text = "Good Job!",
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "You’ve reached your sleep goal the past 4 days!",
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 20.dp)
        )
    }
}

@Composable
fun AddCaffeine(startUiState: StartUiState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 15.dp, end = 7.dp)
            .clip(RoundedCornerShape(9.dp))
            .background(MaterialTheme.colors.primary)
    ) {
        Text(
            text = "Amount of drinks:",
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp)
        )
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "5",
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, bottom = 20.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .padding(top = 0.dp, end = 10.dp, bottom = 10.dp)
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.background)
                    .clickable { }
                    .offset(x = 0.dp, y = 0.dp)

            ) {
                Box() {
                    Icon(
                        Icons.Filled.LocalCafe,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.offset(x = 13.dp, y = 7.dp)
                    )
                    Text(
                        text = "Add",
                        fontSize = 12.sp,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.offset(x = 13.dp, y = 29.dp)
                    )
                }
            }
        }
    }
}