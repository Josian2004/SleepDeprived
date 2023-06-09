package fhict.sm.sleepdeprived.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = Blue,
    primaryVariant = DarkBlue,
    secondary = Gray,
    background = DarkBlue,
    surface = White,
    onPrimary = Gray,
    onSecondary = Gray,
    onBackground = Gray,
    onSurface = Color.Black,
)


@Composable
fun SleepDeprivedTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
//        DarkColorPalette
        LightColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}