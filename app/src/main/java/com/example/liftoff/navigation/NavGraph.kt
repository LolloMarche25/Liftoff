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
import com.example.liftoff.ui.screens.BadgesScreen
import com.example.liftoff.ui.screens.BadgesViewModel
import com.example.liftoff.ui.screens.CheckInDetailScreen
import com.example.liftoff.ui.screens.CheckInsScreen
import com.example.liftoff.ui.screens.CheckInsViewModel
import com.example.liftoff.ui.screens.HomeScreen
import com.example.liftoff.ui.screens.HomeViewModel
import com.example.liftoff.ui.screens.LaunchDetailScreen
import com.example.liftoff.ui.screens.LaunchDetailViewModel
import com.example.liftoff.ui.screens.LaunchesScreen
import com.example.liftoff.ui.screens.LaunchesViewModel
import com.example.liftoff.ui.screens.PersonalNoteScreen
import com.example.liftoff.ui.screens.PersonalNoteViewModel
import com.example.liftoff.ui.screens.ProfileScreen
import com.example.liftoff.ui.screens.ProfileViewModel
import com.example.liftoff.ui.screens.SettingsScreen
import com.example.liftoff.ui.screens.SettingsViewModel
import com.example.liftoff.ui.theme.LiftoffPrimary
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
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
                    onNotifyClick = { homeViewModel.setNextLaunchNotified() },
                    onOptionsClick = { navController.navigate(NavigationRoute.Settings) }
                )
            }
        }
        composable<NavigationRoute.Launches> {
            val launchesViewModel = koinViewModel<LaunchesViewModel>()
            val state by launchesViewModel.state.collectAsStateWithLifecycle()
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = LiftoffPrimary)
                }
            } else {
                LaunchesScreen(
                    navController = navController,
                    launches = state.filteredLaunches,
                    selectedFilter = state.selectedFilter,
                    searchQuery = state.searchQuery,
                    onLaunchClick = { launch ->
                        navController.navigate(NavigationRoute.LaunchDetail(launch.id))
                    },
                    onFilterSelected = { launchesViewModel.setFilter(it) },
                    onSearchQueryChange = { launchesViewModel.setSearchQuery(it) }
                )
            }
        }
        composable<NavigationRoute.CheckIns> {
            val checkInsViewModel = koinViewModel<CheckInsViewModel>()
            val state by checkInsViewModel.state.collectAsStateWithLifecycle()
            CheckInsScreen(
                navController = navController,
                checkIns = state.checkIns,
                onCheckInCLick = { checkIn ->
                    navController.navigate(NavigationRoute.CheckInDetail(checkIn.id))
                }
            )
        }
        composable<NavigationRoute.CheckInDetail> { backStackEntry ->
            val route = backStackEntry.toRoute<NavigationRoute.CheckInDetail>()
            val checkInsViewModel = koinViewModel<CheckInsViewModel>()
            val state by checkInsViewModel.state.collectAsStateWithLifecycle()

            val checkIn = state.checkIns.find { it.id == route.checkInId }
            if (checkIn != null) {
                CheckInDetailScreen(
                    navController = navController,
                    checkIn = checkIn
                )
            }
        }
        composable<NavigationRoute.Badges> {
            val badgesViewModel = koinViewModel<BadgesViewModel>()
            val state by badgesViewModel.state.collectAsStateWithLifecycle()
            BadgesScreen(
                navController = navController,
                badges = state.badges,
                userLevel = state.userLevel,
                userPoints = state.userPoints,
                progressCurrent = state.progressCurrent,
                progressTotal = state.progressTotal
            )
        }
        composable<NavigationRoute.Profile> {
            val profileViewModel = koinViewModel<ProfileViewModel>()
            val state by profileViewModel.state.collectAsStateWithLifecycle()
            ProfileScreen(
                navController = navController,
                username = state.username,
                email = state.email,
                launchesFollowed = state.launchesFollowed,
                checkInsCount = state.checkInsCount,
                badgesUnlocked = state.badgesUnlocked
            )
        }
        composable<NavigationRoute.Settings> {
            val settingsViewModel = koinViewModel<SettingsViewModel>()
            SettingsScreen(
                navController = navController,
                username = settingsViewModel.username,
                email = settingsViewModel.email,
                notificationsEnabled = settingsViewModel.notificationsEnabled,
                onUsernameChange = { settingsViewModel.onUsernameChange(it) },
                onEmailChange = { settingsViewModel.onEmailChange(it) },
                onNotificationsChange = { settingsViewModel.onNotificationsChange(it) }
            )
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
                        detailState = detailState
                    )
                }
            }
        }
        composable<NavigationRoute.PersonalNote> { backStackEntry ->
            val route = backStackEntry.toRoute<NavigationRoute.PersonalNote>()
            val viewModel = koinViewModel<PersonalNoteViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            LaunchedEffect(state.isPosted) {
                if (state.isPosted) {
                    navController.navigate(NavigationRoute.CheckIns) {
                        popUpTo(NavigationRoute.Home) { inclusive = false }
                    }
                }
            }
            PersonalNoteScreen(
                navController = navController,
                launchName = route.launchName,
                note = state.note,
                photoUri = state.photoUri,
                isPosted = state.isPosted,
                newlyUnlockedBadges = state.newlyUnlockedBadges,
                currentBadgeIndex = state.currentBadgeIndex,
                onNoteChange = { viewModel.onNoteChange(it) },
                onPhotoTaken = { viewModel.setPhotoUri(it) },
                onPostCheckIn = {
                    viewModel.postCheckIn(
                        launchId = route.launchId,
                        launchName = route.launchName,
                        date = route.launchDate
                    )
                },
                onDismissBadgeDialog = { viewModel.dismissBadgeDialog() }
            )
        }
    }
}