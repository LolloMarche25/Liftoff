package com.example.liftoff.data.repository

import com.example.liftoff.data.database.CheckInDao
import com.example.liftoff.data.database.CheckInEntity
import kotlinx.coroutines.flow.Flow

class CheckInRepository(private val dao: CheckInDao) {

    fun getAll(): Flow<List<CheckInEntity>> = dao.getAll()

    fun getByLaunchId(launchId: Int): Flow<List<CheckInEntity>> =
        dao.getByLaunchId(launchId)

    suspend fun insert(checkIn: CheckInEntity) = dao.insert(checkIn)
    suspend fun deleteByLaunchId(launchId: Int) = dao.deleteByLaunchId(launchId)
}