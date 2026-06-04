package com.example.liftoff.ui.screens

data class ProfileState(
    val username: String = "Space Explorer",
    val email: String ="something@somethingelse.app",
    val launchesFollowed: Int = 0,
    val checkInsCount: Int = 0,
    val badgesUnlocked: Int = 0
)
