package fhict.sm.sleepdeprived

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.InsertChart
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import fhict.sm.sleepdeprived.ui.navbar.BottomNavItem
import fhict.sm.sleepdeprived.ui.navbar.BottomNavigationBar
import fhict.sm.sleepdeprived.ui.navbar.Navigation
import fhict.sm.sleepdeprived.ui.theme.SleepDeprivedTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /*private val sleepRequestManager by lazy{
        SleepRequestsManager(this)
    }*/


    /*private val permissionRequester: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                requestActivityRecognitionPermission()
            } else {
                sleepRequestManager.subscribeToSleepUpdates()
            }
        }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SleepDeprivedTheme {
                /*var permissionGranted by remember {
                    mutableStateOf(isPermissionGranted())
                }*/

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

        /*sleepRequestManager.requestSleepUpdates(requestPermission = {
            permissionRequester.launch(ACTIVITY_RECOGNITION)
        })*/

        /*//HEALTH CONNECT
        val availabilityStatus = HealthConnectClient.sdkStatus(this)
        if (availabilityStatus == HealthConnectClient.SDK_UNAVAILABLE) {
            //return // early return as there is no viable integration
        }

        val healthConnectClient = HealthConnectClient.getOrCreate(this)


        // build a set of permissions for required data types
        val PERMISSIONS =
            setOf(
                HealthPermission.getReadPermission(SleepSessionRecord::class),
                HealthPermission.getReadPermission(SleepStageRecord::class)
            )

        // Create the permissions launcher.
        val requestPermissionActivityContract = PermissionController.createRequestPermissionResultContract()

        val requestPermissions =
            registerForActivityResult(requestPermissionActivityContract) { granted ->
                if (granted.containsAll(PERMISSIONS)) {
                    // Permissions successfully granted
                    Log.d("HEALTH", "Permission granted")
                } else {
                    // Lack of required permissions
                    Log.d("HEALTH", "Permission not granted")
                }
            }

        suspend fun checkPermissionsAndRun(healthConnectClient: HealthConnectClient) {
            // For alpha09 and earlier versions, use getGrantedPermissions(PERMISSIONS) instead
            Log.d("HEALTH", "checkPermissionAndRun")
            val granted = healthConnectClient.permissionController.getGrantedPermissions()
            if (granted.containsAll(PERMISSIONS)) {
                // Permissions already granted; proceed with inserting or reading data.
            } else {
                requestPermissions.launch(PERMISSIONS)
            }
        }

        lifecycleScope.launch {
            checkPermissionsAndRun(healthConnectClient)
        }*/






    }

    override fun onDestroy() {
        super.onDestroy()
        //sleepRequestManager.unsubscribeFromSleepUpdates()
    }


    /*private fun isPermissionGranted(): Boolean {
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
    }*/

}





//.border(width = 1.dp, Color.Red)