package br.com.codigozeroum.androiddeveloperchallenge.services

import br.com.codigozeroum.androiddeveloperchallenge.models.SearchObjectResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchTitleService {

    @GET("/")
    fun searchMoviesTitle(@Query("s") s: String, @Query("page") page: Int, @Query("apikey") apikey: String): Call<SearchObjectResponse>
}