package fhict.sm.sleepdeprived


import android.app.TimePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.KingBed
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material.icons.outlined.Hotel
import androidx.compose.material.icons.outlined.LocalCafe
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fhict.sm.sleepdeprived.ui.theme.*
import java.util.*


@Composable
fun ScheduleScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .verticalScroll(enabled = true, state = rememberScrollState())
    ) {
        ScheduleHeader()
        Card()

        Spacer(
            modifier = Modifier.height(20.dp)
        )
    }
}

@Composable
fun Card() {
    Text(
        text = "Bedtime and Wake Up",
        fontFamily = aBeeZeeFamily,
        fontWeight = FontWeight.Normal,
        color = MaterialTheme.colors.onPrimary,
        fontSize = 20.sp,
        modifier = Modifier.padding(top = 25.dp, start = 20.dp)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, top = 5.dp)
            .clip(RoundedCornerShape(9.dp))
            .background(MaterialTheme.colors.primary)
            .height(150.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Row(modifier = Modifier.padding(top = 15.dp, bottom = 35.dp)) {
            StartSleep()
            EndSleep()
        }
        Row() {
            Icon(
                Icons.Filled.Hotel,
                contentDescription = null,
                tint = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .offset(x = 0.dp, y = 5.dp)
                    .padding(end = 5.dp)
            )
            Column() {
                Text(text = "8h 12m", fontSize = 20.sp)
                Text(
                    text = "Time Asleep",
                    fontSize = 10.sp,
                    modifier= Modifier.offset(x = 2.dp, y = 0.dp)
                )
            }
        }

    }
}

@Composable
fun StartSleep() {
    // Fetching local context
    val mContext = LocalContext.current

    // Declaring and initializing a calendar
    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    // Value for storing time as a string
    val mTime = remember { mutableStateOf("") }

    // Creating a TimePicker dialog
    val mTimePickerDialog = TimePickerDialog(
        mContext,
        R.style.MyTimePickerStyle,
        { _, mHour: Int, mMinute: Int ->
            if (mMinute < 10) {
                mTime.value = "$mHour:0$mMinute"
            } else {
                mTime.value = "$mHour:$mMinute"
            }
        }, mHour, mMinute, true
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (mTime.value == "") {
            mTime.value = "00:00"
        }
        // On button click, TimePicker is
        // displayed, user can select a time
        Button(
            modifier = Modifier.padding(end = 15.dp),
            onClick = { mTimePickerDialog.show() },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background)
        ) {
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 5.dp),
                imageVector = Icons.Filled.KingBed,
                contentDescription = null
            )
            Text(text = "${mTime.value}", color = Gray, fontSize = 20.sp)
        }
    }
}

@Composable
fun EndSleep() {
    // Fetching local context
    val mContext = LocalContext.current

    // Declaring and initializing a calendar
    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    // Value for storing time as a string
    val mTime = remember { mutableStateOf("") }

    // Creating a TimePicker dialod
    val mTimePickerDialog = TimePickerDialog(
        mContext,
        R.style.MyTimePickerStyle,
        { _, mHour: Int, mMinute: Int ->
            if (mMinute < 10) {
                mTime.value = "$mHour:0$mMinute"
            } else {
                mTime.value = "$mHour:$mMinute"
            }
        }, mHour, mMinute, true
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (mTime.value == "") {
            mTime.value = "00:00"
        }
        // On button click, TimePicker is
        // displayed, user can select a time
        Button(
            onClick = { mTimePickerDialog.show() },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background)
        ) {
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 5.dp),
                imageVector = Icons.Filled.Alarm,
                contentDescription = null
            )
            Text(text = "${mTime.value}", color = Gray, fontSize = 20.sp)
        }
    }
}

@Composable
fun ScheduleHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Text(
            text = "Sleep Schedule",
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
                .background(Gray)

        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_picture),
                contentDescription = "Profile Picture"
            )
        }
    }
}


