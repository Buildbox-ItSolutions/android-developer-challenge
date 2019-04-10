package br.com.codigozeroum.androiddeveloperchallenge.services

import br.com.codigozeroum.androiddeveloperchallenge.models.ResultIMDbId
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchIMDbIdService {

    @GET("/")
    fun searchMovieIMDbId(@Query("i") i: String, @Query("plot") plot: String, @Query("apikey") apikey: String): Call<ResultIMDbId>
}