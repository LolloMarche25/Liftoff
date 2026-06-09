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
                    points >= 1500 -> "Commander"
                    points >= 700 -> "Space Admiral"
                    points >= 300 -> "Astronaut"
                    points >= 100 -> "Cadet"
                    else -> "Rookie"
                }

                val nextBadgeAt = when {
                    count < 1 -> 1
                    count < 3 -> 3
                    count < 5 -> 5
                    count < 7  -> 7
                    count < 10 -> 10
                    count < 15 -> 15
                    else -> 15
                }

                _state.value = BadgesState(
                    badges = generateBadges(count),
                    userLevel = level,
                    userPoints = points,
                    progressCurrent = count,
                    progressTotal = nextBadgeAt
                )
            }
        }
    }

    private fun generateBadges(checkInCount: Int): List<Badge> {
        return listOf(
            Badge(1, "First Launch", "Follow your first launch", "\uD83D\uDE80", checkInCount >= 1),
            Badge(3, "Night Owl", "Add 3 launches to your diary", "\uD83C\uDF16", checkInCount >= 3),
            Badge(2, "Space Enthusiast", "Add 5 launches to your diary", "\uD83D\uDEF8", checkInCount >= 5),
            Badge(4, "Orbiter", "Add 7 launches to your diary", "\uD83D\uDEF0\uFE0F", checkInCount >= 7),
            Badge(5, "Astronaut", "Add 10 launches to your diary", "\uD83E\uDDD1\u200D\uD83D\uDE80", checkInCount >= 10),
            Badge(6, "Space Analyst", "Add 15 launches to your diary", "\uD83D\uDD2D", checkInCount >= 15),
            Badge(7, "???", "Keep exploring!", "🔒", false),
            Badge(8, "???", "Keep exploring!", "🔒", false),
            Badge(9, "???", "Keep exploring!", "🔒", false)
            )
    }
}