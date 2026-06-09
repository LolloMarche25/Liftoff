package com.example.liftoff.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liftoff.data.repository.CheckInRepository
import com.example.liftoff.model.CheckIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CheckInsViewModel(private val repository: CheckInRepository) : ViewModel() {
    private val _state = MutableStateFlow(CheckInsState())
    val state: StateFlow<CheckInsState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAll().collect { entities ->
                _state.value = CheckInsState(
                    checkIns = entities.map { entity ->
                        CheckIn(
                            id = entity.id,
                            launchId = entity.launchId,
                            launchName = entity.launchName,
                            date = entity.date,
                            note = entity.note,
                            photoUri = entity.photoUri,
                            imageUrl = entity.imageUrl
                        )
                    }
                )
            }
        }
    }
}