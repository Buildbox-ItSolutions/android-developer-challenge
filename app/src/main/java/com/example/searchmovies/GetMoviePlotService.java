package com.example.searchmovies;

import com.example.searchmovies.entitys.Search;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
//    @GET("?i={id}&apikey=d2e11186")
public interface GetMoviePlotService {
    @GET("/")
    Call<Search> getMoviePlot(@Query("i") String i , @Query("apikey") String apikey);


}
