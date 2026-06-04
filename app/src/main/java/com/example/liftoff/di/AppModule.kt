package com.example.liftoff.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.liftoff.data.database.LiftoffDatabase
import com.example.liftoff.data.remote.LiftoffDataSource
import com.example.liftoff.data.remote.OSMDataSource
import com.example.liftoff.data.repository.CheckInRepository
import com.example.liftoff.data.repository.LaunchRepository
import com.example.liftoff.data.repository.SettingsRepository
import com.example.liftoff.ui.screens.BadgesViewModel
import com.example.liftoff.ui.screens.CheckInsViewModel
import com.example.liftoff.ui.screens.HomeViewModel
import com.example.liftoff.ui.screens.LaunchDetailViewModel
import com.example.liftoff.ui.screens.LaunchesViewModel
import com.example.liftoff.ui.screens.PersonalNoteViewModel
import com.example.liftoff.ui.screens.ProfileViewModel
import com.example.liftoff.ui.screens.SettingsViewModel

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
val appModule = module {
    single {
        Room.databaseBuilder(
            get(),
            LiftoffDatabase::class.java,
            "liftoff-database"
        ).fallbackToDestructiveMigration().build()
    }

    single { get<LiftoffDatabase>().checkInDao()}
    single { get<Context>().dataStore }

    single { LiftoffDataSource() }
    single { LaunchRepository(get()) }
    single { CheckInRepository(get()) }
    single { OSMDataSource() }
    single { SettingsRepository(get()) }

    viewModel { HomeViewModel(get()) }
    viewModel { LaunchesViewModel(get()) }
    viewModel { CheckInsViewModel(get()) }
    viewModel { BadgesViewModel(get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { LaunchDetailViewModel(get(), get()) }
    viewModel { PersonalNoteViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
}