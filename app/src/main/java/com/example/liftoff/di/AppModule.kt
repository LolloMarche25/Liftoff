package com.example.liftoff.di

import com.example.liftoff.data.remote.LiftoffDataSource
import com.example.liftoff.data.repository.LaunchRepository
import com.example.liftoff.ui.screens.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { LiftoffDataSource() }
    single { LaunchRepository(get()) }
    viewModel { HomeViewModel(get()) }
}