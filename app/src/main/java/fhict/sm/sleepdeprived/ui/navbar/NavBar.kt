package fhict.sm.sleepdeprived.ui.navbar


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import fhict.sm.sleepdeprived.HomeScreen
import fhict.sm.sleepdeprived.InfoScreen
import fhict.sm.sleepdeprived.ScheduleScreen
import fhict.sm.sleepdeprived.ui.infoscreen.TipDetailScreen
import fhict.sm.sleepdeprived.ui.startscreen.StartScreen
import fhict.sm.sleepdeprived.ui.theme.Blue
import fhict.sm.sleepdeprived.ui.theme.DarkBlue
import fhict.sm.sleepdeprived.ui.theme.Gray
import fhict.sm.sleepdeprived.ui.theme.White


@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "start") {
        composable("start") {
            StartScreen()
        }
        composable("stats") {
            HomeScreen()
        }
        composable("info") {
            InfoScreen()
        }
        composable("sleep") {
            ScheduleScreen()
        }


    }
}

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemclick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    BottomNavigation(
        modifier = Modifier
            .height(72.dp)
            .border(width = 2.dp, DarkBlue)
            .clip(
                shape = RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            ),
        backgroundColor = Blue,
        elevation = 5.dp,

        ){
        items.forEach{ item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemclick(item)},
                selectedContentColor = White,
                unselectedContentColor = Gray,
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (item.badgeCount > 0){
                            BadgedBox( badge = {
                                Badge {
                                    Text(item.badgeCount.toString())
                                }
                            }){ Icon(
                                modifier = Modifier.size(32.dp),
                                imageVector = item.icon,
                                contentDescription = item.name
                            )
                            }
                        }
                        else {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                imageVector = item.icon,
                                contentDescription = item.name
                            )
                        }

//                        if(selected) {
//                            Text(
//                                text = item.name,
//                                textAlign = TextAlign.Center,
//                                fontSize = 10.sp
//                            )
//                        }
                    }
                }

            )
        }
    }

}
