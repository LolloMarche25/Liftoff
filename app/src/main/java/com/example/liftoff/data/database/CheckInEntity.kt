package com.example.liftoff.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "check_ins")
data class CheckInEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val launchId: Int,
    val launchName: String,
    val date: String,
    val note: String = "",
    val photoUri: String = ""
)