package com.example.searchmovies;


import com.example.searchmovies.entitys.SearchMovieEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetMovieDataService {
   // @GET("?s=batman&apikey=d2e11186")
    @GET("/")
    Call<SearchMovieEntity> getMovieData(@Query("s") String s , @Query("apikey") String apikey);

}
