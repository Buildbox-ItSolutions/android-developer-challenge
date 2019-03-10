package com.example.movies.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.movies.api.OmdbInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Database {

    val call = OmdbInstance().movieService()


    private val dataMovieList: MutableLiveData<List<Movie>> = MutableLiveData()
    private val dataMovieDetail: MutableLiveData<Movie> = MutableLiveData()

    fun insertMovies(s: String, apikey: String, type: String): MutableLiveData<List<Movie>> {
        call.list(s,apikey,type).clone().enqueue(object : Callback<MovieList> {

            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                Log.d("OnResponse","RESPONSE")
                dataMovieList.postValue(response.body()?.search)
            }

            override fun onFailure(call: Call<MovieList>, t: Throwable) {
                Log.d("OnFail","error" + t.message)
            }
        })

        return dataMovieList
    }

    fun insertMovieDetail (i: String, apikey: String) : MutableLiveData<Movie>
    {
        call.detail(i,apikey).clone().enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                Log.d("OnResponseDetail","RESPONSE")
                dataMovieDetail.postValue(response.body())
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.d("OnFailDetail","FAILED")
            }

        })

        return dataMovieDetail
    }

}