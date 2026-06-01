package com.example.liftoff.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liftoff.data.repository.LaunchRepository
import com.example.liftoff.model.Launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel(private val repository: LaunchRepository) : ViewModel() {
    private val _state = MutableStateFlow<HomeState?>(null)
    val state: StateFlow<HomeState?> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val launches = repository.getUpcomingLaunches()
                _state.value = HomeState(
                    nextLaunch = launches.first(),
                    upcomingLaunches = launches.drop(1)
                )
                startCountdownTimer()
            } catch (e: Exception) {

            }
        }
    }

    private fun startCountdownTimer() {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                val currentState = _state.value ?: return@launch
                _state.value = currentState.copy(
                    nextLaunch = recalculateCountdown(currentState.nextLaunch)
                )
            }
        }
    }

    private fun recalculateCountdown(launch: Launch): Launch {
        if (launch.netUtc.isEmpty()) return launch
        val launchInstant = java.time.Instant.parse(launch.netUtc)
        val now = java.time.Instant.now()
        val duration = java.time.Duration.between(now, launchInstant)
        val totalSeconds = duration.seconds.coerceAtLeast(0)
        return launch.copy(
            daysLeft = (totalSeconds / 86400).toInt(),
            hoursLeft = ((totalSeconds % 86400) / 3600).toInt(),
            minutesLeft = ((totalSeconds % 3600) / 60).toInt(),
            secondsLeft = (totalSeconds % 60).toInt()
        )
    }

    fun setNextLaunchNotified() {
        _state.value = _state.value?.copy(isNextLaunchNotified = true)
    }
}