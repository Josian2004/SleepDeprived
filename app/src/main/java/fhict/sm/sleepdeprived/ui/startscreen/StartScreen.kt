package fhict.sm.sleepdeprived.ui.startscreen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fhict.sm.sleepdeprived.R
import fhict.sm.sleepdeprived.ui.theme.aBeeZeeFamily

@Composable
fun StartScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .verticalScroll(enabled = true, state = rememberScrollState())
    ) {

        StartHeader()
        Character()
        RateSleepSlider()
        NightSummary()
        Row() {
            Box(modifier = Modifier.weight(1f)) {
                AddCaffeine()
            }
            Box(modifier = Modifier.weight(1f)) {
                GoalSummary()
            }
        }

        Spacer(modifier = Modifier.height(20.dp)
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
fun Character() {
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
fun RateSleepSlider() {
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
            modifier = Modifier.padding(top = 10.dp, bottom = 15.dp, start = 10.dp, end = 10.dp)
        )

        Box(modifier = Modifier.height(30.dp)) {
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
                ),
                modifier = Modifier.padding(start = 50.dp, end = 50.dp, bottom = 20.dp)
            )
        }

    }
}

@Composable
fun NightSummary() {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .clip(RoundedCornerShape(9.dp))
            .width(300.dp)
            .background(MaterialTheme.colors.primary),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Wow, you slept like a programmer!",
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Today you slept for 4 hours and 21 minutes, that’s really bad, it may have been because you drank 12 coffees before going to bed...\n" +
                        "\n" +
                        "Maybe look at some tips on how to improve your sleep.",
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            )
        }
}

@Composable
fun GoalSummary() {
    Column(modifier = Modifier
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
fun AddCaffeine() {
    Column(modifier = Modifier
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