package com.example.liftoff.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liftoff.data.repository.SettingsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsViewModel(private val repository: SettingsRepository) : ViewModel() {
    var username by mutableStateOf("")
        private set

    var notificationsEnabled by mutableStateOf(true)
        private set

    init {
        viewModelScope.launch {
            username = repository.username.first()
            notificationsEnabled = repository.notificationsEnabled.first()
        }
    }

    fun onUsernameChange(value: String) {
        username = value
        viewModelScope.launch { repository.setUsername(value) }
    }

    fun onNotificationsChange(enabled: Boolean) {
        notificationsEnabled = enabled
        viewModelScope.launch { repository.setNotificationsEnabled(enabled) }
    }
}