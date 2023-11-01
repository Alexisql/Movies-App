package com.alexis.moviesapp.domain.repository

import com.alexis.moviesapp.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface IMovieDetailRepository {

    fun getDetailMovie(idMovie: Int): Flow<Result<MovieDetail>>
}