package com.example.liftoff.data.remote.dto

import android.location.Location
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LaunchResponseDto(
    val results: List<LaunchDto>
)

@Serializable
data class LaunchDto(
    val id: String,
    val name: String,
    val net: String,
    val status: StatusDto,
    val rocket: RocketDto,
    val pad: PadDto,
    @SerialName("launch_service_provider")
    val launchServiceProvider: AgencyDto,
    val mission: MissionDto? = null
)

@Serializable
data class StatusDto(val name: String)

@Serializable
data class AgencyDto(val name: String)

@Serializable
data class RocketDto(val configuration: RocketConfigDto)

@Serializable
data class RocketConfigDto(val name: String)

@Serializable
data class PadDto(
    val name: String,
    val location: LocationDto
)

@Serializable
data class LocationDto(val name: String)

@Serializable
data class MissionDto(
    val name: String,
    val description: String
)