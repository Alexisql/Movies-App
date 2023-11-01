package com.alexis.moviesapp.data.retrofit.repository

import com.alexis.moviesapp.data.retrofit.response.toMovieDetailDomain
import com.alexis.moviesapp.data.retrofit.service.MoviePopularService
import com.alexis.moviesapp.domain.model.MovieDetail
import com.alexis.moviesapp.domain.repository.IMovieDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieDetailRepositoryRetrofitImpl @Inject constructor(
    private val movieService: MoviePopularService
) : IMovieDetailRepository {
    override fun getDetailMovie(idMovie: Int): Flow<Result<MovieDetail>> {
        return flow {
            try {
                val response = movieService.getDetailMovie(idMovie)
                emit(Result.success(response.toMovieDetailDomain()))
            } catch (exception: Throwable) {
                emit(Result.failure(exception))
            }
        }
    }
}