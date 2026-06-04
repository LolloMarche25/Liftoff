package com.example.liftoff.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liftoff.data.database.CheckInEntity
import com.example.liftoff.data.repository.CheckInRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PersonalNoteViewModel(
    private val checkInRepository: CheckInRepository
) : ViewModel() {
    private val _state = MutableStateFlow(PersonalNoteState())
    val state: StateFlow<PersonalNoteState> = _state.asStateFlow()

    fun onNoteChange(note: String) {
        if (note.length <= 280) {
            _state.value = _state.value.copy(note = note)
        }
    }

    fun postCheckIn(launchId: Int, launchName: String, date: String) {
        viewModelScope.launch {
            checkInRepository.insert(
                CheckInEntity(
                    launchId = launchId,
                    launchName = launchName,
                    date = date,
                    note = _state.value.note
                )
            )
            _state.value = _state.value.copy(isPosted = true)
        }
    }
}