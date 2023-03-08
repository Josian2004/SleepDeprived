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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import fhict.sm.sleepdeprived.ui.theme.aBeeZeeFamily
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, top = 15.dp)
            .clip(RoundedCornerShape(9.dp))
            .background(MaterialTheme.colors.primary)
            .height(240.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        StartSleep()
        EndSleep()
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

    // Creating a TimePicker dialod
    val mTimePickerDialog = TimePickerDialog(
        mContext,
        { _, mHour: Int, mMinute: Int ->
            if (mMinute < 10) {
                mTime.value = "$mHour:0$mMinute"
            } else {
                mTime.value = "$mHour:$mMinute"
            }
        }, mHour, mMinute, false
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
            Text(text = "${mTime.value}", color = Color.White)
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
        { _, mHour: Int, mMinute: Int ->
            mTime.value = "$mHour:$mMinute"
        }, mHour, mMinute, false
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (mTime.value != null) {
            mTime.value = "00:00"
        }
        // On button click, TimePicker is
        // displayed, user can select a time
        Button(
            onClick = { mTimePickerDialog.show() },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background)
        ) {
            Text(text = "${mTime.value}", color = Color.White)
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
                .background(Color.Gray)

        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_picture),
                contentDescription = "Profile Picture"
            )
        }
    }
}


