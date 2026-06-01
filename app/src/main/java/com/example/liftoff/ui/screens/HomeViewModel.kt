package com.example.liftoff.ui.screens

import androidx.lifecycle.ViewModel
import com.example.liftoff.model.Launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val _state = MutableStateFlow(
        HomeState(
            nextLaunch = Launch(
                id = 1,
                name = "Starlink Group 6-42",
                rocket = "Falcon 9",
                agency = "SpaceX",
                location = "Kennedy Space Center, FL",
                status = "Scheduled",
                date = "May 30",
                daysLeft = 2,
                hoursLeft = 14,
                minutesLeft = 29,
                secondsLeft = 58,
                description = "This mission will deploy 60 Starlink satellites."
            ),
            upcomingLaunches = listOf(
                Launch(
                    id = 2,
                    name = "Artemis III",
                    rocket = "SLS Block 1B",
                    agency = "NASA",
                    location = "Kennedy Space Center, FL",
                    status = "Scheduled",
                    date = "Jun 15"
                ),
                Launch(
                    id = 3,
                    name = "JWST Servicing Mission",
                    rocket = "Ariane 6",
                    agency = "ESA",
                    location = "Kourou, French Guiana",
                    status = "Scheduled",
                    date = "Jun 22"
                )
            )
        )
    )

    val state: StateFlow<HomeState> = _state.asStateFlow()
}