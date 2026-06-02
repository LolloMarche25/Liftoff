package com.example.liftoff.ui.screens

import com.example.liftoff.model.Badge

data class BadgesState(
    val badges: List<Badge> = emptyList(),
    val userLevel: String = "Rookie",
    val userPoints: Int = 0,
    val progressCurrent: Int = 0,
    val progressTotal: Int = 9
)
