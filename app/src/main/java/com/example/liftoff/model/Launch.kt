package com.example.liftoff.model

data class Launch(
    val name: String,
    val rocket: String,
    val agency: String,
    val location: String,
    val status: String,
    val daysLeft: Int = 0,
    val hoursLeft: Int = 0,
    val minutesLeft: Int = 0,
    val secondsLeft: Int = 0,
    val description: String = ""
)