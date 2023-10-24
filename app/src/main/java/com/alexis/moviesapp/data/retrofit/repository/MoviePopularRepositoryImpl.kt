package com.alexis.moviesapp.data.retrofit.repository

import com.alexis.moviesapp.data.retrofit.response.toDomain
import com.alexis.moviesapp.data.retrofit.service.MoviePopularService
import com.alexis.moviesapp.domain.model.Movie
import com.alexis.moviesapp.domain.repository.IMoviePopularRepository
import javax.inject.Inject

class MoviePopularRepositoryImpl @Inject constructor(
    private val moviePopularService: MoviePopularService
) : IMoviePopularRepository {
    override suspend fun getMovies(): Result<List<Movie>> {
        return try {
            val response = moviePopularService.getMovies().movies
            Result.success(response.map { it.toDomain() })
        } catch (exception: Throwable) {
            Result.failure(exception)
        }
    }
}