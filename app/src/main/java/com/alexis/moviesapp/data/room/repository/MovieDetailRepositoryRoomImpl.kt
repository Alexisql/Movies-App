package com.alexis.moviesapp.data.room.repository

import com.alexis.moviesapp.data.core.Constants.MOVIE_NOT_FOUND
import com.alexis.moviesapp.data.room.dao.MovieDao
import com.alexis.moviesapp.data.room.entities.toMovieDetailDomain
import com.alexis.moviesapp.domain.model.MovieDetail
import com.alexis.moviesapp.domain.repository.IMovieDetailRepository
import javax.inject.Inject

class MovieDetailRepositoryRoomImpl @Inject constructor(
    private val movieDao: MovieDao
) : IMovieDetailRepository {
    override suspend fun getDetailMovie(idMovie: Int): Result<MovieDetail> {
        val movie = movieDao.getDetailMovie(idMovie)
        return movie?.let {
            Result.success(it.toMovieDetailDomain())
        } ?: Result.failure(Exception(MOVIE_NOT_FOUND))
    }
}