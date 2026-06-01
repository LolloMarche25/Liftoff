package com.example.liftoff.data.remote

import com.example.liftoff.data.remote.dto.OSMPlace
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class OSMDataSource {

    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun searchLocation(query: String): OSMPlace? {
        return try {
            val results: List<OSMPlace> = client.get(
                "https://nominatim.openstreetmap.org/search?q=${query.replace(" ", "%20")}&format=json&limit=1"
            ) {
                headers.append("User-Agent", "LiftoffApp/1.0")
            }.body()
            results.firstOrNull()
        } catch (e: Exception) {
            null
        }
    }
}