package com.example.movies.dependencies_injection

import com.example.movies.data.Database
import com.example.movies.data.MovieManager
import com.example.movies.viewmodels.MoviesViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

object DiModules {

    val appModule = module {

        single { Database() }

        factory { MovieManager(get()) }

        viewModel { MoviesViewModel(get()) }
    }
}