package com.example.movies.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Movie {
    @SerializedName("Title")
    @Expose
    private var title: String? = null

    @SerializedName("Poster")
    @Expose
    private var poster: String? = null

    @SerializedName("Year")
    @Expose
    private var year: String? = null

    @SerializedName("Type")
    @Expose
    private var type: String? = null

    @SerializedName("imdbID")
    @Expose
    private var imdbid: String? = null

    @SerializedName("Plot")
    @Expose
    private var plot: String? = null

    fun setTitle(Title: String) {
        title = Title
    }

    fun getTitle() = title


    fun setPoster(Poster: String) {
        poster = Poster
    }

    fun getPoster() = poster

    fun setYear(Year: String) {
        year = Year
    }

    fun getYear() = year

    fun setType(Type: String) {
        type = Type
    }

    fun getType() = type

    fun setImdbid(Imdbid: String) {
        imdbid = Imdbid
    }

    fun getImdbid() = imdbid

    fun setPlot(Plot: String) {
        plot = Plot
    }

    fun getPlot() = plot


}