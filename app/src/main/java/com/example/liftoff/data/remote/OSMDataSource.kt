package com.example.liftoff.data.remote

import com.example.liftoff.data.remote.dto.OSMPlace
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class OSMDataSource(private val httpClient: HttpClient) {

    suspend fun searchLocation(query: String): OSMPlace? {
        return try {
            val results: List<OSMPlace> = httpClient.get(
                "https://nominatim.openstreetmap.org/search?q=$query&format=json&limit=1"
            ).body()
            results.firstOrNull()
        } catch (e: Exception) {
            null
        }
    }
}