package fhict.sm.sleepdeprived

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.LocalCafe
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fhict.sm.sleepdeprived.ui.theme.aBeeZeeFamily


@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .verticalScroll(enabled = true, state = rememberScrollState())
    ) {
        Header()
        History()
        Data()
        Caffeine()
    }
}

@Composable
fun Header() {
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
fun History() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, top = 5.dp)
            .clip(RoundedCornerShape(9.dp))
            .background(MaterialTheme.colors.primary)
            .height(80.dp)

    ) {

    }
}

@Composable
fun Data() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, top = 15.dp)
            .clip(RoundedCornerShape(9.dp))
            .background(MaterialTheme.colors.primary)
            .height(300.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .height(35.dp)
                    .width(100.dp)
            ) {
                Icon(
                    Icons.Outlined.Bedtime,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.offset(x = 0.dp, y = 5.dp)
                )
                Text(
                    text = "8h 12m",
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.offset(x = 23.dp, y = 0.dp)
                )
                Text(
                    text = "Time Asleep",
                    fontSize = 10.sp,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.offset(x = 25.dp, y = 20.dp)
                )
            }
            Spacer(Modifier.width(20.dp).height(5.dp))
            Box(
                modifier = Modifier
                    .width(170.dp)
                    .height(35.dp)
            ) {
                Icon(
                    Icons.Filled.Schedule,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.offset(x = (-2).dp, y = 5.dp)
                )
                Text(
                    text = "01:13-09:32",
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.offset(x = 23.dp, y = 0.dp)
                )
                Text(
                    text = "From-Til",
                    fontSize = 10.sp,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.offset(x = 25.dp, y = 20.dp)
                )
            }
        }
        Box(
            modifier = Modifier
                .width(300.dp)
                .padding(top = 0.dp)
                .clip(RoundedCornerShape(9.dp))
                .background(MaterialTheme.colors.background)
                .height(200.dp)

        ) {

        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.height(30.dp)) {
                var sliderPosition by remember { mutableStateOf(0f) }
                Slider(
                    value = sliderPosition,
                    onValueChange = { sliderPosition = it },
                    valueRange = 1f..10f,
                    steps = 8,

                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colors.onPrimary,
                        activeTickColor = MaterialTheme.colors.background,
                        inactiveTickColor = MaterialTheme.colors.background,
                        activeTrackColor = MaterialTheme.colors.onPrimary
                    ),
                    modifier = Modifier.padding(start = 50.dp, end = 50.dp)
                )
            }
            Text(
                text = "How do you rate your night?",
                fontSize = 16.sp,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Composable
fun Caffeine() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, top = 15.dp)
            .clip(RoundedCornerShape(9.dp))
            .background(MaterialTheme.colors.primary)
            .height(240.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .height(35.dp)
                    .width(122.dp)
            ) {
                Icon(
                    Icons.Outlined.LocalCafe,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.offset(x = 0.dp, y = 5.dp)
                )
                Text(
                    text = "6",
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.offset(x = 26.dp, y = 0.dp)
                )
                Text(
                    text = "Amount of Drinks",
                    fontSize = 10.sp,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.offset(x = 27.dp, y = 20.dp)
                )
            }
            Spacer(Modifier.width(20.dp).height(5.dp))
            Box(
                modifier = Modifier
                    .width(137.dp)
                    .height(35.dp)

            ) {
                Icon(
                    Icons.Filled.Schedule,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.offset(x = (-2).dp, y = 5.dp)
                )
                Text(
                    text = "1h 58m",
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.offset(x = 23.dp, y = 0.dp)
                )
                Text(
                    text = "Time since last Drink",
                    fontSize = 10.sp,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.offset(x = 25.dp, y = 20.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .width(300.dp)
                .padding(top = 0.dp)
                .clip(RoundedCornerShape(9.dp))
                .background(MaterialTheme.colors.background)
                .height(130.dp)

        ) {

        }
        Box(
            modifier = Modifier
                .padding(top = 5.dp)
                .size(50.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colors.background)

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