package com.example.movies.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MovieList {
    @SerializedName("Search")
    @Expose
    private var search: List<Movie>? = null

    fun setSearch(Search: List<Movie>){
        search = Search
    }

    fun getSearch() = search

}