package com.alexis.moviesapp.data.core.di

import com.alexis.moviesapp.data.core.AuthInterceptor
import com.alexis.moviesapp.data.core.Constants.BASE_URL
import com.alexis.moviesapp.data.retrofit.repository.MoviePopularRepositoryImpl
import com.alexis.moviesapp.data.retrofit.service.MoviePopularService
import com.alexis.moviesapp.domain.repository.IMoviePopularRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun interceptorOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun moviePopularService(retrofit: Retrofit): MoviePopularService {
        return retrofit.create(MoviePopularService::class.java)
    }


    @Singleton
    @Provides
    fun movieRepository(moviePopularService: MoviePopularService): IMoviePopularRepository {
        return MoviePopularRepositoryImpl(moviePopularService)
    }

}