package com.example.liftoff.data.repository

import com.example.liftoff.data.remote.LiftoffDataSource
import com.example.liftoff.data.remote.dto.LaunchDto
import com.example.liftoff.model.Launch

class LaunchRepository(private val dataSource: LiftoffDataSource) {
    suspend fun getUpcomingLaunches(): List<Launch> {
        return dataSource.getUpcomingLaunches()
            .mapIndexed { index, dto -> dto.toDomain(index + 1) }
            .filter { launch ->
                launch.daysLeft > 0 ||
                launch.hoursLeft > 0 ||
                launch.minutesLeft > 0 ||
                launch.secondsLeft > 0
            }
    }
}

private fun LaunchDto.toDomain(id: Int): Launch {
    val launchTime = java.time.ZonedDateTime.parse(net)
    val now = java.time.ZonedDateTime.now()
    val duration = java.time.Duration.between(now, launchTime)

    val totalSeconds = duration.seconds.coerceAtLeast(0)
    val days = totalSeconds / 86400
    val hours = (totalSeconds % 86400) / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60

    return Launch(
        id = id,
        name = name,
        rocket = rocket.configuration.name,
        agency = launchServiceProvider.name,
        location = pad.location.name,
        status = status.name,
        date = net.substring(0,  10),
        netUtc = net,
        imageUrl = image ?: "",
        daysLeft = days.toInt(),
        hoursLeft = hours.toInt(),
        minutesLeft = minutes.toInt(),
        secondsLeft = seconds.toInt(),
        description = mission?.description ?: ""
    )
}