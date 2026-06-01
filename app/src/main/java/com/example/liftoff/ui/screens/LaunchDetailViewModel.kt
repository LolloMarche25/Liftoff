package com.example.liftoff.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liftoff.data.database.CheckInEntity
import com.example.liftoff.data.repository.CheckInRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LaunchDetailViewModel(
    private val checkInRepository: CheckInRepository
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

    fun toggleCheckIn(launchId: Int, launchName: String, date: String) {
        viewModelScope.launch {
            if (_state.value.isCheckedIn) {
                checkInRepository.deleteByLaunchId(launchId)
            } else {
                checkInRepository.insert(
                    CheckInEntity(
                        launchId = launchId,
                        launchName = launchName,
                        date = date
                    )
                )
            }
        }
    }
}