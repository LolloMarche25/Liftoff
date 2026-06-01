package com.example.liftoff.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liftoff.data.repository.LaunchRepository
import com.example.liftoff.model.Launch
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
            } catch (e: Exception) {

            }
        }
    }
}