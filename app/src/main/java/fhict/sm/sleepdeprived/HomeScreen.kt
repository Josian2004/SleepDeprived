package fhict.sm.sleepdeprived

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fhict.sm.sleepdeprived.ui.theme.aBeeZeeFamily
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily


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
        Spacer(modifier = Modifier.height(10.dp))
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
    val SleptHours = arrayOf(4f, 7f, 6f, 7f, 3f, 8f, 4f, 5f, 6f, 7f,)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, top = 5.dp)
            .clip(RoundedCornerShape(9.dp))
            .background(MaterialTheme.colors.primary)
            .height(80.dp)
            .horizontalScroll(enabled = true, state = rememberScrollState(), reverseScrolling = true)

    ) {
        progress(SleptHours)
    }
}

@Composable
fun progress(SleptHours: Array<Float>) {
    val Days = arrayOf("21/2", "22/2", "23/2", "24/2", "25/2", "26/2", "27/2", "28/2", "1/3", "2/3")
    var lastDayDP = 0.dp
    var i = 0
    Box(){
        Row() {
            Days.forEach { item ->
                i++
                if(i == Days.size){
                    lastDayDP = 15.dp
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.padding(start = 15.dp, top = 10.dp, end = lastDayDP) ) {
                    CircularProgressbar2(SleptHours[i-1])
                    Text(
                        text = item,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

        }
    }
}

@Composable
fun CircularProgressbar2(
    number: Float,
    numberStyle: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    size: Dp = 40.dp,
    thickness: Dp = 8.dp,
    animationDuration: Int = 1000,
    animationDelay: Int = 0,
    foregroundIndicatorColor: Color = MaterialTheme.colors.onPrimary,
    backgroundIndicatorColor: Color = MaterialTheme.colors.background,
    extraSizeForegroundIndicator: Dp = 0.dp
) {

    // It remembers the number value
    var numberR by remember {
        mutableStateOf(-1f)
    }

    // Number Animation
    val animateNumber = animateFloatAsState(
        targetValue = numberR,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay
        )
    )

    // This is to start the animation when the activity is opened
    LaunchedEffect(Unit) {
        numberR = number
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size = size)
    ) {
        Canvas(
            modifier = Modifier
                .size(size = size)
        ) {

            // Background circle
            drawCircle(
                color = backgroundIndicatorColor,
                radius = size.toPx() / 2,
                style = Stroke(width = thickness.toPx(), cap = StrokeCap.Round)
            )

            val sleepGoal = 8
            val sweepAngle = (animateNumber.value / sleepGoal) * 360

            // Foreground circle
            drawArc(
                color = foregroundIndicatorColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke((thickness + extraSizeForegroundIndicator).toPx(), cap = StrokeCap.Butt)
            )
        }

        // Text that shows number inside the circle
        Text(
            text = (animateNumber.value).toInt().toString() + "h",
            style = numberStyle,

        )
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
            Spacer(
                Modifier
                    .width(20.dp)
                    .height(5.dp))
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
            Spacer(
                Modifier
                    .width(20.dp)
                    .height(5.dp))
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