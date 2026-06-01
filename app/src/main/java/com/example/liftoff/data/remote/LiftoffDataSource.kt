package com.example.liftoff.data.remote

import com.example.liftoff.data.remote.dto.LaunchDto
import com.example.liftoff.data.remote.dto.LaunchResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class LiftoffDataSource {

    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun getUpcomingLaunches(): List<LaunchDto> {
        val response: LaunchResponseDto = client.get(
            "https://ll.thespacedevs.com/2.2.0/launch/upcoming/?limit=10"
        ).body()
        return response.results
    }
}