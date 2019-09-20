package com.example.movies.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movies.data.Movie
import com.example.movies.data.MovieManager

class MoviesViewModel(val movieManager: MovieManager): ViewModel() {

    private var mMovies = MutableLiveData<List<Movie>>()

    private var mMovieDetail = MutableLiveData<Movie>()
    fun getMovies(): LiveData<List<Movie>> = mMovies

    fun loadMovies(s: String, apikey: String, type: String){
            mMovies = movieManager.getMovies(s, apikey, type)
    }

    fun getMovieDetail(): LiveData<Movie> = mMovieDetail

    fun loadMovieDetail(i: String, apikey: String)
    {
        mMovieDetail = movieManager.getMovieDetail(i,apikey)
    }



}