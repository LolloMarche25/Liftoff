package com.example.liftoff.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liftoff.data.repository.CheckInRepository
import com.example.liftoff.data.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val checkInRepository: CheckInRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            settingsRepository.username.collect { username ->
                _state.value = _state.value.copy(username = username)
            }
        }
        viewModelScope.launch {
            settingsRepository.email.collect { email ->
                _state.value = _state.value.copy(email = email)
            }
        }
        viewModelScope.launch {
            checkInRepository.getAll().collect { checkIns ->
                val count = checkIns.size
                _state.value = _state.value.copy(
                    checkInsCount = count,
                    launchesFollowed = count,
                    badgesUnlocked = when {
                        count >= 10 -> 4
                        count >= 5 -> 3
                        count >= 3 -> 2
                        count >= 1 -> 1
                        else -> 0
                    }
                )
            }
        }
    }
}