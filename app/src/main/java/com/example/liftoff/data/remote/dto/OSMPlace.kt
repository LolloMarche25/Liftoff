package com.example.liftoff.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OSMPlace(
    @SerialName("place_id")
    val id: Long,
    @SerialName("lat")
    val latitude: String,
    @SerialName("lon")
    val longitude: String,
    @SerialName("display_name")
    val displayName: String
)
