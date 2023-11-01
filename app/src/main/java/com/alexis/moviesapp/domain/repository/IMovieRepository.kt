package com.alexis.moviesapp.domain.repository

import com.alexis.moviesapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getMovies(): Flow<Result<List<Movie>>>
    fun addMovie(movie: Movie):Flow<Result<String>>
    fun deleteMovie(idMovie: Int):Flow<Result<String>>
}