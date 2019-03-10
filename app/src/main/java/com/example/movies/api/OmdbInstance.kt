package com.example.movies.api

import com.example.movies.api.interfaces.GetMovieDataService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OmdbInstance {
    private var retrofit: Retrofit? = null
    fun getOmdbInstance(): Retrofit {
        if (retrofit == null) {
            retrofit = retrofit2.Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

    fun movieService()  = getOmdbInstance().create(GetMovieDataService::class.java)

}