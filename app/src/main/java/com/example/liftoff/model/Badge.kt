package com.example.liftoff.model

data class Badge(
    val id: Int,
    val name: String,
    val description: String,
    val emoji: String,
    val isUnlocked: Boolean
)