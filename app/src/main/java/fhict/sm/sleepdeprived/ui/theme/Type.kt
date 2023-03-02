package fhict.sm.sleepdeprived.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import fhict.sm.sleepdeprived.R

val aBeeZeeFamily = FontFamily(
    Font(R.font.abeezee_regular, FontWeight.Normal),
    Font(R.font.abeezee_italic, FontWeight.Normal, FontStyle.Italic),
)

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = aBeeZeeFamily

    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)