package com.example.liftoff.navigation

import kotlinx.serialization.Serializable

sealed interface NavigationRoute {
    @Serializable
    data object Home : NavigationRoute
    @Serializable
    data class LaunchDetail(val launchId: Int) : NavigationRoute
}