package com.example.liftoff.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liftoff.data.repository.CheckInRepository
import com.example.liftoff.model.Badge
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BadgesViewModel(private val repository: CheckInRepository) : ViewModel() {
    private val _state = MutableStateFlow(BadgesState())
    val state: StateFlow<BadgesState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAll().collect { checkIns ->
                val count = checkIns.size
                val points = count * 100
                val level = when {
                    points >= 500 -> "Commander"
                    points >= 300 -> "Astronaut"
                    points >= 100 -> "Cadet"
                    else -> "Rookie"
                }
                _state.value = BadgesState(
                    badges = generateBadges(count),
                    userLevel = level,
                    userPoints = points,
                    progressCurrent = count % 9,
                    progressTotal = 9
                )
            }
        }
    }

    private fun generateBadges(checkInCount: Int): List<Badge> {
        return listOf(
            Badge(1, "First Launch", "Checked in to your first launch", "\uD83D\uDE80", checkInCount >= 1),
            Badge(2, "Space Enthusiast", "Tracked 5 launches", "⭐", checkInCount >= 5),
            Badge(3, "Night Owl", "Checked in to a night launch", "\uD83C\uDF19", checkInCount >= 3),
            Badge(4, "SpaceX Fan", "Tracked 10 SpaceX launches", "🔥", checkInCount >= 10),
            Badge(5, "Early Bird", "Tracked 5 launches in a row", "🐦", false),
            Badge(6, "Globetrotter", "Tracked launches from 5 sites", "🌍", false),
            Badge(7, "???", "Keep exploring!", "🔒", false),
            Badge(8, "???", "Keep exploring!", "🔒", false),
            Badge(9, "???", "Keep exploring!", "🔒", false)
            )
    }
}