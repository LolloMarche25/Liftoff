package com.example.liftoff.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CheckInEntity::class], version = 2)
abstract class LiftoffDatabase : RoomDatabase() {
    abstract fun checkInDao(): CheckInDao
}