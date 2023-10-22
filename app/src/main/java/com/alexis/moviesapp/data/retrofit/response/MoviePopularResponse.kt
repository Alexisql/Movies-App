package com.alexis.moviesapp.data.retrofit.response

import com.google.gson.annotations.SerializedName

data class MoviePopularResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<MovieResponse>
)