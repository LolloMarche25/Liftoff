package com.example.liftoff.di

import com.example.liftoff.ui.screens.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { HomeViewModel() }
}