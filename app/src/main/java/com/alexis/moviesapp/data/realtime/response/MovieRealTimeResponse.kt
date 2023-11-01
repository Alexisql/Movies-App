package com.alexis.moviesapp.data.realtime.response

import com.alexis.moviesapp.domain.model.Movie
import com.alexis.moviesapp.domain.model.MovieDetail
import com.google.firebase.database.IgnoreExtraProperties

data class MovieRealTimeResponse(
    var id: Int = 0,
    val originalTitle: String = "",
    val overview: String = "",
    val backdropPath: String = "",
    val posterPath: String = "",
    val releaseDate: String = "",
)

fun MovieRealTimeResponse.toDomain() =
    Movie(id, originalTitle, overview, backdropPath, posterPath, releaseDate)

fun Movie.toData() =
    MovieRealTimeResponse(id, originalTitle, overview, backdropPath, posterPath, releaseDate)

fun MovieRealTimeResponse.toMovieDetailDomain() = MovieDetail(toDomain(), true)