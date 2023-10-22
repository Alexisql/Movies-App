package com.alexis.moviesapp.domain.repository

import com.alexis.moviesapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getMovies(): Flow<List<Movie>>
    suspend fun addMovie(movie: Movie):Result<String>
    suspend fun deleteMovie(idMovie: Int):Result<String>
}