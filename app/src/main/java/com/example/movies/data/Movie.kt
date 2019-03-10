package com.example.movies.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Movie
(
    @SerializedName("Title")
    @Expose
    var title: String,

    @SerializedName("Poster")
    @Expose
    var poster: String,

    @SerializedName("Year")
    @Expose
    var year: String,

    @SerializedName("Type")
    @Expose
    var type: String,

    @SerializedName ("imdbID")
    @Expose
    var  imdbid: String,

    @SerializedName ("Plot")
    @Expose
    var plot: String
)