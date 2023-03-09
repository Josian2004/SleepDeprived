package fhict.sm.sleepdeprived

import android.Manifest
import android.Manifest.permission.ACTIVITY_RECOGNITION
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.InsertChart
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import fhict.sm.sleepdeprived.data.sleep.SleepRequestsManager
import fhict.sm.sleepdeprived.ui.navbar.BottomNavItem
import fhict.sm.sleepdeprived.ui.navbar.BottomNavigationBar
import fhict.sm.sleepdeprived.ui.navbar.Navigation
import fhict.sm.sleepdeprived.ui.theme.SleepDeprivedTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val sleepRequestManager by lazy{
        SleepRequestsManager(this)
    }

    private val permissionRequester: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                requestActivityRecognitionPermission()
            } else {
                sleepRequestManager.subscribeToSleepUpdates()
            }
        }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SleepDeprivedTheme {
                var permissionGranted by remember {
                    mutableStateOf(isPermissionGranted())
                }


                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                val navController = rememberNavController()
                 Scaffold(
                        bottomBar = {
                            BottomNavigationBar(
                                items = listOf(
                                    BottomNavItem(
                                        name = "Home",
                                        route = "start",
                                        icon = Icons.Filled.Home,
                                    ),
                                    BottomNavItem(
                                        name = "Tips for You",
                                        route = "info",
                                        icon = Icons.Filled.Info
                                    ),
                                    BottomNavItem(
                                        name = "Stats",
                                        route = "stats",
                                        icon = Icons.Filled.InsertChart,
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
                    ) {innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            Navigation(navController = navController)
                        }
                    }

                }
        }

        sleepRequestManager.requestSleepUpdates(requestPermission = {
            permissionRequester.launch(ACTIVITY_RECOGNITION)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        sleepRequestManager.unsubscribeFromSleepUpdates()
    }


    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACTIVITY_RECOGNITION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestActivityRecognitionPermission() {
        val intent = Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        startActivity(intent)
    }

}





//.border(width = 1.dp, Color.Red)