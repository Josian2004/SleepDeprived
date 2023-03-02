package fhict.sm.sleepdeprived

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import fhict.sm.sleepdeprived.ui.theme.SleepDeprivedTheme
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SleepDeprivedTheme {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            items = listOf(
                                BottomNavItem(
                                    name = "Tips for You",
                                    route = "info",
                                    icon = Icons.Filled.Info
                                ),
                                BottomNavItem(
                                    name = "Home",
                                    route = "home",
                                    icon = Icons.Filled.Home,
                                ),
                                BottomNavItem(
                                    name = "Sleep Schedule",
                                    route = "sleep",
                                    icon = Icons.Filled.WatchLater,
                                ),
                            ),
                            navController = navController,
                            onItemclick = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                ) {
                    Navigation(navController = navController)
                }

            }
        }
    }

}

@Composable
fun InfoScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Sleep Tips screen")
    }
}

@Composable
fun SleepScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Sleep Schedule screen")

    }
}


//.border(width = 1.dp, Color.Red)