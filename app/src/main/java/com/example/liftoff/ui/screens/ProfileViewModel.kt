package com.example.liftoff.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liftoff.data.repository.CheckInRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: CheckInRepository) : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAll().collect { checkIns ->
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