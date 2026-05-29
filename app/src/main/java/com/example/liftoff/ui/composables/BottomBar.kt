package com.example.liftoff.ui.composables

import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.liftoff.ui.theme.LiftoffBackground
import com.example.liftoff.ui.theme.LiftoffPrimary
import com.example.liftoff.ui.theme.LiftoffSurfaceVariant
import com.example.liftoff.ui.theme.LiftoffTextSecondary

data class BottomBarItem(
    val label: String,
    val icon: ImageVector
)

val bottomBarItems = listOf(
    BottomBarItem("Home", Icons.Outlined.Home),
    BottomBarItem("Launches", Icons.Outlined.RocketLaunch),
    BottomBarItem("Check-ins", Icons.Outlined.CameraAlt),
    BottomBarItem("Badges", Icons.Outlined.MilitaryTech),
    BottomBarItem("Profile", Icons.Outlined.Person)
)

@Composable
fun LiftoffBottomBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    NavigationBar(
        containerColor = LiftoffBackground
    ) {
        bottomBarItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = { onItemSelected(index) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
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