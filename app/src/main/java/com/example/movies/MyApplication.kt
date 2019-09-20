package com.example.movies

import android.app.Application
import com.example.movies.dependencies_injection.DiModules
import org.koin.android.ext.android.startKoin

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(DiModules.appModule))
    }
}