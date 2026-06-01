package com.example.liftoff

import android.app.Application
import com.example.liftoff.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class LiftoffApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@LiftoffApplication)
            modules(appModule)
        }
    }
}