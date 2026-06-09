package com.example.liftoff.data.remote

import com.example.liftoff.data.remote.dto.LaunchResponseDto
import com.example.liftoff.data.remote.dto.LaunchDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class LiftoffDataSource(private val httpClient: HttpClient) {

    suspend fun getUpcomingLaunches(): List<LaunchDto> {
        val response: LaunchResponseDto = httpClient.get(
            "https://ll.thespacedevs.com/2.2.0/launch/upcoming/?limit=10"
        ).body()
        return response.results
    }
}