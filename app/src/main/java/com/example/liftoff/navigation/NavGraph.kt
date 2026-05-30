package com.example.liftoff.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.liftoff.model.Launch
import com.example.liftoff.ui.screens.HomeScreen
import com.example.liftoff.ui.screens.LaunchDetailScreen
import com.example.liftoff.ui.screens.LaunchesScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    launches: List<Launch>
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Home
    ) {
        composable<NavigationRoute.Home> {
            HomeScreen(
                navController = navController,
                nextLaunch = launches.first(),
                upcomingLaunches = launches.drop(1),
                onLaunchClick = { launch ->
                    navController.navigate(NavigationRoute.LaunchDetail(launch.id))
                }
            )
        }
        composable<NavigationRoute.Launches> {
            LaunchesScreen(
                navController = navController,
                launches = launches
            )
        }
        composable<NavigationRoute.LaunchDetail> { backStackEntry ->
            val route = backStackEntry.toRoute<NavigationRoute.LaunchDetail>()
            val launch = launches.find { it.id == route.launchId }
            if (launch != null) {
                LaunchDetailScreen(
                    navController = navController,
                    launch = launch
                )
            }
        }
    }
}