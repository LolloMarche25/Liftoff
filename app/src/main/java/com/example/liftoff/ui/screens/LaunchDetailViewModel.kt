package com.example.liftoff.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liftoff.data.remote.OSMDataSource
import com.example.liftoff.data.repository.CheckInRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LaunchDetailViewModel(
    private val checkInRepository: CheckInRepository,
    private val osmDataSource: OSMDataSource
) : ViewModel() {
    private val _state = MutableStateFlow(LaunchDetailState())
    val state: StateFlow<LaunchDetailState> = _state.asStateFlow()

    fun loadCheckInStatus(launchId: Int) {
        viewModelScope.launch {
            checkInRepository.getByLaunchId(launchId).collect { checkIns ->
                _state.value = _state.value.copy(isCheckedIn = checkIns.isNotEmpty())
            }
        }
    }

    fun loadLaunchSiteLocation(location: String) {
        viewModelScope.launch {
            try {
                val place = osmDataSource.searchLocation(location)
                if (place != null) {
                    _state.value = _state.value.copy(
                        latitude = place.latitude,
                        longitude = place.longitude,
                        displayName = place.displayName                    )
                }
            } catch (e: Exception) {

            }
        }
    }
}