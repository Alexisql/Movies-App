package com.alexis.moviesapp.data.retrofit.service

import com.alexis.moviesapp.data.retrofit.response.MoviePopularResponse
import com.alexis.moviesapp.data.retrofit.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviePopularService {
    @GET("popular")
    suspend fun getMovies(): MoviePopularResponse

    @GET("{idMovie}")
    suspend fun getDetailMovie(@Path("idMovie") idMovie: Int): MovieResponse

}