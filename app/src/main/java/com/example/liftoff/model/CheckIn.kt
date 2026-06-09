package com.example.liftoff.model

data class CheckIn(
    val id: Int,
    val launchId: Int,
    val launchName: String,
    val date: String,
    val note: String = "",
    val photoUri: String = "",
    val imageUrl: String = ""
)