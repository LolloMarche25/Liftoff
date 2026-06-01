package com.example.liftoff.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.liftoff.model.Badge
import com.example.liftoff.model.CheckIn
import com.example.liftoff.model.Launch
import com.example.liftoff.ui.screens.BadgesScreen
import com.example.liftoff.ui.screens.CheckInsScreen
import com.example.liftoff.ui.screens.HomeScreen
import com.example.liftoff.ui.screens.HomeViewModel
import com.example.liftoff.ui.screens.LaunchDetailScreen
import com.example.liftoff.ui.screens.LaunchDetailViewModel
import com.example.liftoff.ui.screens.LaunchesScreen
import com.example.liftoff.ui.screens.ProfileScreen
import com.example.liftoff.ui.theme.LiftoffPrimary
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Home
    ) {
        composable<NavigationRoute.Home> {
            val homeViewModel = koinViewModel<HomeViewModel>()
            val state by homeViewModel.state.collectAsStateWithLifecycle()

            if (state == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = LiftoffPrimary)
                }
            } else {
                HomeScreen(
                    navController = navController,
                    nextLaunch = state!!.nextLaunch,
                    upcomingLaunches = state!!.upcomingLaunches,
                    isNextLaunchNotified = state!!.isNextLaunchNotified,
                    onLaunchClick = { launch ->
                        navController.navigate(NavigationRoute.LaunchDetail(launch.id))
                    },
                    onNotifyClick = { homeViewModel.setNextLaunchNotified() }
                )
            }
        }
        /*
        composable<NavigationRoute.Launches> {
            LaunchesScreen(
                navController = navController,
                launches = launches
            )
        }
        composable<NavigationRoute.CheckIns> {
            val fakeCheckIns = listOf(
                CheckIn(1, "Starlink Group 6-42", "May 30, 2026", "Incredible night launch! 🚀"),
                CheckIn(2, "Artemis III", "Jun 15, 2026", "Historic moment - returning to the Moon!"),
                CheckIn(3, "Crew Dragon Demo-5", "Apr 10, 2026", "Beautiful day for a launch"),
                CheckIn(4, "OneWeb Launch 20", "Mar 22, 2026", "Witnessed it from the beach!")
            )
            CheckInsScreen(
                navController = navController,
                checkIns = fakeCheckIns
            )
        } */
        composable<NavigationRoute.Badges> {
            val fakeBadges = listOf(
                Badge(1, "First Launch", "Checked in to your first launch", "\uD83D\uDE80", true),
                Badge(2, "Space Enthusiast", "Tracked 5 launches", "⭐", true),
                Badge(3, "Night Owl", "Checked in to a night launch", "\uD83C\uDF19", true),
                Badge(4, "SpaceX Fan", "Tracked 10 SpaceX launches", "🔥", true),
                Badge(5, "Early Bird", "Tracked 5 launches in a row", "\uD83D\uDC26", false),
                Badge(6, "Globetrotter", "Tracked launches from 5 different sites", "\uD83C\uDF0D", false),
                Badge(7, "???", "Keep exploring!", "\uD83D\uDD12", false),
                Badge(8, "???", "Keep exploring!", "\uD83D\uDD12", false),
                Badge(9, "???", "Keep exploring!", "\uD83D\uDD12", false)
            )
            BadgesScreen(
                navController = navController,
                badges = fakeBadges
            )
        }
        composable<NavigationRoute.Profile> {
            ProfileScreen(navController = navController)
        }
        composable<NavigationRoute.LaunchDetail> { backStackEntry ->
            val homeViewModel = koinViewModel<HomeViewModel>()
            val state by homeViewModel.state.collectAsStateWithLifecycle()
            val route = backStackEntry.toRoute<NavigationRoute.LaunchDetail>()
            val detailViewModel = koinViewModel<LaunchDetailViewModel>()
            val detailState by detailViewModel.state.collectAsStateWithLifecycle()

            state?.let { homeState ->
                val allLaunches = listOf(homeState.nextLaunch) + homeState.upcomingLaunches
                val launch = allLaunches.find { it.id == route.launchId }
                if (launch != null) {

                    LaunchedEffect(route.launchId) {
                        detailViewModel.loadCheckInStatus(route.launchId)
                        detailViewModel.loadLaunchSiteLocation(launch.location)
                    }

                    LaunchDetailScreen(
                        navController = navController,
                        launch = launch,
                        detailState = detailState,
                        onCheckInClick = {
                            detailViewModel.toggleCheckIn(
                                launchId = launch.id,
                                launchName = launch.name,
                                date = launch.date
                            )
                        }
                    )
                }
            }
        }
    }
}