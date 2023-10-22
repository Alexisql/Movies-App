package com.alexis.moviesapp.data.retrofit.repository

import com.alexis.moviesapp.data.retrofit.response.toMovieDetailDomain
import com.alexis.moviesapp.data.retrofit.service.MoviePopularService
import com.alexis.moviesapp.domain.model.MovieDetail
import com.alexis.moviesapp.domain.repository.IMovieDetailRepository
import javax.inject.Inject

class MovieDetailRepositoryRetrofitImpl @Inject constructor(
    private val movieService: MoviePopularService
) : IMovieDetailRepository {
    override suspend fun getDetailMovie(idMovie: Int): Result<MovieDetail> {
        return try {
            val response = movieService.getDetailMovie(idMovie)
            Result.success(response.toMovieDetailDomain())
        } catch (exception: Throwable) {
            Result.failure(exception)
        }
    }
}