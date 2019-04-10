package br.com.codigozeroum.androiddeveloperchallenge.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://www.omdbapi.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun searchTitleService() = retrofit.create(SearchTitleService::class.java)
    fun searchTitleTypeService() = retrofit.create(SearchTitleTypeService::class.java)
    fun searchTitleYearService() = retrofit.create(SearchTitleYearService::class.java)

    fun searchMovieIMDbIdService() = retrofit.create(SearchIMDbIdService::class.java)

}