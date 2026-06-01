package com.example.liftoff.ui.screens

import com.example.liftoff.model.Launch

data class HomeState(
    val nextLaunch: Launch,
    val upcomingLaunches: List<Launch>,
    val isNextLaunchNotified: Boolean = false
)