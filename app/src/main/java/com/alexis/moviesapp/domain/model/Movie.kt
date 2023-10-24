package com.alexis.moviesapp.domain.model

import com.alexis.moviesapp.domain.core.Constants.URL_BASE_POSTER

data class Movie(
    var id: Int,
    val originalTitle: String,
    val overview: String,
    val backdropPath: String,
    val posterPath: String,
    val releaseDate: String
){
    val posterPathFull get() = "$URL_BASE_POSTER$posterPath"
    val backdropPathFull get() = "$URL_BASE_POSTER$backdropPath"
}