package com.example.liftoff.data.repository

import com.example.liftoff.data.remote.LiftoffDataSource
import com.example.liftoff.data.remote.dto.LaunchDto
import com.example.liftoff.model.Launch

class LaunchRepository(private val dataSource: LiftoffDataSource) {
    suspend fun getUpcomingLaunches(): List<Launch> {
        return dataSource.getUpcomingLaunches().mapIndexed { index, dto ->
            dto.toDomain(index + 1)
        }
    }
}

private fun LaunchDto.toDomain(id: Int): Launch {
    return Launch(
        id = id,
        name = name,
        rocket = rocket.configuration.name,
        agency = launchServiceProvider.name,
        location = pad.location.name,
        status = status.name,
        date = net.substring(0,  10),
        description = mission?.description ?: ""
    )
}