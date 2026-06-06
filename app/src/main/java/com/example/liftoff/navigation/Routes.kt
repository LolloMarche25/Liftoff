package com.example.liftoff.navigation
import kotlinx.serialization.Serializable
sealed interface NavigationRoute {
    @Serializable
    data object Home : NavigationRoute
    @Serializable
    data object Launches : NavigationRoute
    @Serializable
    data object CheckIns : NavigationRoute
    @Serializable
    data class CheckInDetail(val checkInId: Int) : NavigationRoute
    @Serializable
    data object Badges : NavigationRoute
    @Serializable
    data object Profile : NavigationRoute
    @Serializable
    data object Settings : NavigationRoute
    @Serializable
    data class LaunchDetail(val launchId: Int) : NavigationRoute
    @Serializable
    data class PersonalNote(
        val launchId: Int,
        val launchName: String,
        val launchDate: String,
        val launchImageUrl: String = ""
    ) : NavigationRoute
}