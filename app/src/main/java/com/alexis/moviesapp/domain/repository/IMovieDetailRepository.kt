package com.alexis.moviesapp.domain.repository

import com.alexis.moviesapp.domain.model.MovieDetail

interface IMovieDetailRepository {

    suspend fun getDetailMovie(idMovie: Int): Result<MovieDetail>
}