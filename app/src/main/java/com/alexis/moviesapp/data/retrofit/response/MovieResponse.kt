package com.alexis.moviesapp.data.retrofit.response

import com.alexis.moviesapp.domain.model.Movie
import com.alexis.moviesapp.domain.model.MovieDetail
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("original_title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("release_date") val releaseDate: String,
)

fun MovieResponse.toDomain() =
    Movie(id, title, overview, backdropPath, posterPath, releaseDate)

fun MovieResponse.toMovieDetailDomain() = MovieDetail(toDomain(), false)

