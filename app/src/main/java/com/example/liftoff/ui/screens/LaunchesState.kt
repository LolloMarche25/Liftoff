package com.example.liftoff.ui.screens

import com.example.liftoff.model.Launch

data class LaunchesState(
    val launches: List<Launch> = emptyList(),
    val filteredLaunches: List<Launch> = emptyList(),
    val selectedFilter: String = "All",
    val searchQuery: String = "",
    val isLoading: Boolean = true
)
