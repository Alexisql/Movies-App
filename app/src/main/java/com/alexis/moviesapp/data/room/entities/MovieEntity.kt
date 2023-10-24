package com.alexis.moviesapp.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexis.moviesapp.domain.model.Movie
import com.alexis.moviesapp.domain.model.MovieDetail

@Entity(tableName = "Movie")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo("id") var id: Int,
    @ColumnInfo("original_title") val originalTitle: String,
    @ColumnInfo("overview") val overview: String,
    @ColumnInfo("backdrop_path") val backdropPath: String,
    @ColumnInfo("poster_path") val posterPath: String,
    @ColumnInfo("release_date") val releaseDate: String,
)

fun MovieEntity.toDomain() =
    Movie(id, originalTitle, overview, backdropPath, posterPath, releaseDate)

fun Movie.toData() =
    MovieEntity(id, originalTitle, overview, backdropPath, posterPath, releaseDate)

fun MovieEntity.toMovieDetailDomain() = MovieDetail(toDomain(), true)