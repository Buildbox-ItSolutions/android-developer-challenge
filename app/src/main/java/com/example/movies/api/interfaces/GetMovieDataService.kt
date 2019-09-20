package com.example.movies.api.interfaces

import com.example.movies.data.Movie
import com.example.movies.data.MovieList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetMovieDataService {

    @GET("/")
    abstract fun list(@Query("s") s: String, @Query("apikey") apikey: String, @Query("type") type: String): Call<MovieList>
    @GET("/")
    abstract fun detail(@Query("i") s: String, @Query("apikey") apikey: String): Call<Movie>


}