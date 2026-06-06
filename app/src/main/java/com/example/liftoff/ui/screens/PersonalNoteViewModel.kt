package com.example.liftoff.ui.screens

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liftoff.data.database.CheckInEntity
import com.example.liftoff.data.repository.CheckInRepository
import com.example.liftoff.model.Badge
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
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

    fun setPhotoUri(uri: Uri) {
        _state.value = _state.value.copy(photoUri = uri)
    }

    fun postCheckIn(launchId: Int, launchName: String, date: String, imageUrl: String) {
        viewModelScope.launch {
            val beforeCount = checkInRepository.getAll().first().size

            checkInRepository.insert(
                CheckInEntity(
                    launchId = launchId,
                    launchName = launchName,
                    date = date,
                    note = _state.value.note,
                    photoUri = _state.value.photoUri?.toString() ?: "",
                    imageUrl = imageUrl
                )
            )

            val afterCount = beforeCount + 1
            val newBadges = checkNewBadges(beforeCount, afterCount)

            if (newBadges.isEmpty()) {
                _state.value = _state.value.copy(isPosted = true)
            } else {
                _state.value = _state.value.copy(newlyUnlockedBadges = newBadges)
            }
        }
    }

    fun dismissBadgeDialog() {
        val current = _state.value
        val nextIndex = current.currentBadgeIndex + 1
        if (nextIndex >= current.newlyUnlockedBadges.size) {
            _state.value = current.copy(isPosted = true)
        } else {
            _state.value = current.copy(currentBadgeIndex = nextIndex)
        }
    }

    private fun checkNewBadges(before: Int, after: Int): List<Badge> {
        val badges = mutableListOf<Badge>()
        if (before < 1 && after >= 1)
            badges.add(Badge(1, "First Launch", "Checked in to your first launch", "🚀", true))
        if (before < 3 && after >= 3)
            badges.add(Badge(3, "Night Owl", "Checked in to a night launch", "🌙", true))
        if (before < 5 && after >= 5)
            badges.add(Badge(2, "Space Enthusiast", "Tracked 5 launches", "⭐", true))
        if (before < 10 && after >= 10)
            badges.add(Badge(4, "SpaceX Fan", "Tracked 10 SpaceX launches", "🔥", true))
        return badges
    }
}