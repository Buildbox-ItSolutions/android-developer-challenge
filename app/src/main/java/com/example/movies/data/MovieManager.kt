package com.example.movies.data



class MovieManager(val database: Database) {

    fun getMovies(s: String, apikey: String, type: String) = database.insertMovies(s,apikey,type)


    fun getMovieDetail (i: String, apikey: String) = database.insertMovieDetail(i,apikey)


}


