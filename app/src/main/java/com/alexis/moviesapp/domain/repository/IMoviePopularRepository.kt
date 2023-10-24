package com.alexis.moviesapp.domain.repository

import com.alexis.moviesapp.domain.model.Movie

interface IMoviePopularRepository {
    suspend fun getMovies(): Result<List<Movie>>
}