package com.example.liftoff.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CheckInDao {
    @Insert
    suspend fun insert(checkIn: CheckInEntity)

    @Query("SELECT * FROM check_ins ORDER BY id DESC")
    fun getAll(): Flow<List<CheckInEntity>>

    @Query("SELECT * FROM check_ins WHERE launchId = :launchId")
    fun getByLaunchId(launchId: Int): Flow<List<CheckInEntity>>

    @Query("DELETE FROM check_ins WHERE launchId = :launchId")
    suspend fun deleteByLaunchId(launchId: Int)
}