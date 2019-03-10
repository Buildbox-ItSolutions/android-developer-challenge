package com.example.movies.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MovieList {
    @SerializedName("Search")
    @Expose
    var search: List<Movie>? = null

}