package com.alexis.moviesapp.data.room.repository

import com.alexis.moviesapp.data.core.Constants.MOVIE_ADDED
import com.alexis.moviesapp.data.core.Constants.MOVIE_DELETED
import com.alexis.moviesapp.data.core.Constants.MOVIE_NOT_ADDED
import com.alexis.moviesapp.data.core.Constants.MOVIE_NOT_DELETED
import com.alexis.moviesapp.data.core.Constants.RESPONSE_ERROR_ROOM
import com.alexis.moviesapp.data.room.dao.MovieDao
import com.alexis.moviesapp.data.room.entities.toData
import com.alexis.moviesapp.data.room.entities.toDomain
import com.alexis.moviesapp.domain.model.Movie
import com.alexis.moviesapp.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao
) : IMovieRepository {
    override fun getMovies(): Flow<List<Movie>> {
        val response = movieDao.getMovies()
        return response.map {
            it.map { movieEntity -> movieEntity.toDomain() }
        }
    }

    override suspend fun addMovie(movie: Movie): Result<String> {
        val response = movieDao.insertMovie(movie.toData())
        return if (response > RESPONSE_ERROR_ROOM) {
            Result.success(MOVIE_ADDED)
        } else {
            Result.failure(Exception(MOVIE_NOT_ADDED))
        }
    }

    override suspend fun deleteMovie(idMovie: Int): Result<String> {
        val response = movieDao.deleteMovie(idMovie)
        return if (response > RESPONSE_ERROR_ROOM) {
            Result.success(MOVIE_DELETED)
        } else {
            Result.failure(Exception(MOVIE_NOT_DELETED))
        }
    }
}