package com.testebuildbox.testebuildbox.api;

import com.testebuildbox.testebuildbox.model.GaleriaInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by fjesus on 05/10/2018.
 */

/**
 * Interface de configuração do retrofit.
 */
public interface UnsplashService {

    public static final String BASE_URL = "https://api.unsplash.com/";

    @Headers("Authorization: Client-ID 64441a26d1e0fcc909e039168052274882d3f1b223188f3ba776370a78309688")
    @GET("photos/curated/")
    Call<List<GaleriaInfo>> fotosAleatoris();
}
