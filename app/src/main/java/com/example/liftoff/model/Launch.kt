package com.example.liftoff.model

data class Launch(
    val id: Int,
    val name: String,
    val rocket: String,
    val agency: String,
    val location: String,
    val status: String,
    val date: String = "",
    val netUtc: String = "",
    val imageUrl: String = "",
    val description: String = "",
    val videoUrl: String = "",
    val daysLeft: Int = 0,
    val hoursLeft: Int = 0,
    val minutesLeft: Int = 0,
    val secondsLeft: Int = 0,

    )