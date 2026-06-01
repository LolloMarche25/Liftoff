package com.example.liftoff.di

import androidx.room.Room
import com.example.liftoff.data.database.LiftoffDatabase
import com.example.liftoff.data.remote.LiftoffDataSource
import com.example.liftoff.data.remote.OSMDataSource
import com.example.liftoff.data.repository.CheckInRepository
import com.example.liftoff.data.repository.LaunchRepository
import com.example.liftoff.ui.screens.HomeViewModel
import com.example.liftoff.ui.screens.LaunchDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            get(),
            LiftoffDatabase::class.java,
            "liftoff-database"
        ).build()
    }

    single { get<LiftoffDatabase>().checkInDao()}

    single { LiftoffDataSource() }
    single { LaunchRepository(get()) }
    single { CheckInRepository(get()) }
    single { OSMDataSource() }

    viewModel { HomeViewModel(get()) }
    viewModel { LaunchDetailViewModel(get(), get()) }
}