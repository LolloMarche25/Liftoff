package com.example.liftoff.ui.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MilitaryTech
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.RocketLaunch
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.liftoff.navigation.NavigationRoute
import com.example.liftoff.ui.theme.LiftoffBackground
import com.example.liftoff.ui.theme.LiftoffPrimary
import com.example.liftoff.ui.theme.LiftoffSurfaceVariant
import com.example.liftoff.ui.theme.LiftoffTextSecondary

data class BottomBarItem(
    val label: String,
    val icon: ImageVector
)

val bottomBarItems = listOf(
    BottomBarItem("Home",      Icons.Outlined.Home),
    BottomBarItem("Launches",  Icons.Outlined.RocketLaunch),
    BottomBarItem("Diary", Icons.Outlined.Book),
    BottomBarItem("Badges",    Icons.Outlined.MilitaryTech),
    BottomBarItem("Profile",   Icons.Outlined.Person)
)

@Composable
fun LiftoffBottomBar(navController: NavHostController) {
    val currentBackStack by navController.currentBackStackEntryAsState()

    val selectedIndex = when (currentBackStack?.destination?.route) {
        NavigationRoute.Home::class.qualifiedName -> 0
        NavigationRoute.Launches::class.qualifiedName -> 1
        NavigationRoute.CheckIns::class.qualifiedName -> 2
        NavigationRoute.Badges::class.qualifiedName -> 3
        NavigationRoute.Profile::class.qualifiedName -> 4
        else -> 0
    }

    NavigationBar(containerColor = LiftoffBackground) {
        bottomBarItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = {
                    when (index) {
                        0 -> navController.navigate(NavigationRoute.Home)
                        1 -> navController.navigate(NavigationRoute.Launches)
                        2 -> navController.navigate(NavigationRoute.CheckIns)
                        3 -> navController.navigate(NavigationRoute.Badges)
                        4 -> navController.navigate(NavigationRoute.Profile)
                        else -> { /*TODO*/ }
                    }
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.label)
                },
                label = {
                    Text(text = item.label)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = LiftoffPrimary,
                    selectedTextColor = LiftoffPrimary,
                    indicatorColor = LiftoffSurfaceVariant,
                    unselectedIconColor = LiftoffTextSecondary,
                    unselectedTextColor = LiftoffTextSecondary
                )
            )
        }
    }
}