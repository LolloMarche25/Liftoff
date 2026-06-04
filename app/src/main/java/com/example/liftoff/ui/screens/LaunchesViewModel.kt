package com.example.liftoff.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.liftoff.data.repository.LaunchRepository
import com.example.liftoff.model.Launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.logging.Filter

class LaunchesViewModel(private val repository: LaunchRepository) : ViewModel() {
    private val _state = MutableStateFlow(LaunchesState())
    val state: StateFlow<LaunchesState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val launches = repository.getUpcomingLaunches()
                _state.value = LaunchesState(
                    launches = launches,
                    filteredLaunches = launches,
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = LaunchesState(isLoading = false)
            }
        }
    }

    fun setFilter(filter: String) {
        val current = _state.value
        _state.value = current.copy(
            selectedFilter = filter,
            filteredLaunches = applyFilters(current.launches, filter, current.searchQuery)
        )
    }

    fun setSearchQuery(query: String) {
        val current = _state.value
        _state.value = current.copy(
            searchQuery = query,
            filteredLaunches = applyFilters(current.launches, current.selectedFilter, query)
        )
    }

    private fun applyFilters(
        launches: List<Launch>,
        filter: String,
        query: String
    ): List<Launch> {
        val knownAgencies = listOf("SpaceX", "NASA", "ESA")

        return launches
            .filter { launch ->
                when (filter) {
                    "All" -> true
                    "Other" -> knownAgencies.none {
                        launch.agency.contains(it, ignoreCase = true)
                    }
                    else -> launch.agency.contains(filter, ignoreCase = true)
                }
            }
            .filter { launch ->
                query.isEmpty() || launch.name.contains(query, ignoreCase = true)
            }
    }
}