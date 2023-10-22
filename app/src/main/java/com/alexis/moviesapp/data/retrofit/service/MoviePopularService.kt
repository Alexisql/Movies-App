package com.alexis.moviesapp.data.retrofit.service

import com.alexis.moviesapp.data.retrofit.response.MoviePopularResponse
import retrofit2.http.GET

interface MoviePopularService {
    @GET("popular")
    suspend fun getMovies(): MoviePopularResponse

}