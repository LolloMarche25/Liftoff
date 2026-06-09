package com.example.liftoff.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.liftoff.data.database.LiftoffDatabase
import com.example.liftoff.data.remote.LiftoffDataSource
import com.example.liftoff.data.remote.OSMDataSource
import com.example.liftoff.data.repository.AuthRepository
import com.example.liftoff.data.repository.CheckInRepository
import com.example.liftoff.data.repository.LaunchRepository
import com.example.liftoff.data.repository.SettingsRepository
import com.example.liftoff.ui.screens.AuthViewModel
import com.example.liftoff.ui.screens.BadgesViewModel
import com.example.liftoff.ui.screens.CheckInsViewModel
import com.example.liftoff.ui.screens.HomeViewModel
import com.example.liftoff.ui.screens.LaunchDetailViewModel
import com.example.liftoff.ui.screens.LaunchesViewModel
import com.example.liftoff.ui.screens.PersonalNoteViewModel
import com.example.liftoff.ui.screens.ProfileViewModel
import com.example.liftoff.ui.screens.SettingsViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

val appModule = module {
    single {
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
            defaultRequest {
                headers.append(HttpHeaders.UserAgent, "LiftoffApp/1.0")
            }
        }
    }

    single {
        Room.databaseBuilder(
            get(),
            LiftoffDatabase::class.java,
            "liftoff-database"
        ).fallbackToDestructiveMigration().build()
    }
    single { get<LiftoffDatabase>().checkInDao() }

    single { get<Context>().dataStore }

    single { LiftoffDataSource(get()) }
    single { OSMDataSource(get()) }

    single { LaunchRepository(get()) }
    single { CheckInRepository(get()) }
    single { SettingsRepository(get()) }
    single { AuthRepository() }

    viewModel { HomeViewModel(get(), get()) }
    viewModel { LaunchesViewModel(get()) }
    viewModel { CheckInsViewModel(get()) }
    viewModel { BadgesViewModel(get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { LaunchDetailViewModel(get(), get()) }
    viewModel { PersonalNoteViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { AuthViewModel(get(), get()) }
}