package com.alexis.moviesapp.data.core.di

import android.content.Context
import androidx.room.Room
import com.alexis.moviesapp.data.core.AuthInterceptor
import com.alexis.moviesapp.data.core.Constants.BASE_URL
import com.alexis.moviesapp.data.core.Constants.MOVIE_DATA_BASE_NAME
import com.alexis.moviesapp.data.retrofit.repository.MovieDetailRepositoryRetrofitImpl
import com.alexis.moviesapp.data.retrofit.repository.MoviePopularRepositoryImpl
import com.alexis.moviesapp.data.retrofit.service.MoviePopularService
import com.alexis.moviesapp.data.room.MovieDataBase
import com.alexis.moviesapp.data.room.dao.MovieDao
import com.alexis.moviesapp.data.room.repository.MovieDetailRepositoryRoomImpl
import com.alexis.moviesapp.data.room.repository.MovieRepositoryImpl
import com.alexis.moviesapp.domain.repository.IMovieDetailRepository
import com.alexis.moviesapp.domain.repository.IMoviePopularRepository
import com.alexis.moviesapp.domain.repository.IMovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MovieDetailRepositoryRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MovieDetailRepositoryRoom

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

    @MovieDetailRepositoryRetrofit
    @Provides
    @Singleton
    fun provideMovieDetailRepositoryRetrofit(moviePopularService: MoviePopularService): IMovieDetailRepository{
        return MovieDetailRepositoryRetrofitImpl(moviePopularService)
    }

    @Singleton
    @Provides
    fun providerRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, MovieDataBase::class.java, MOVIE_DATA_BASE_NAME).build()

    @Singleton
    @Provides
    fun providerMovieDao(dataBase: MovieDataBase) = dataBase.getMovieDao()

    @Singleton
    @Provides
    fun providerMovieRepository(movieDao: MovieDao): IMovieRepository {
        return MovieRepositoryImpl(movieDao)
    }

    @MovieDetailRepositoryRoom
    @Provides
    @Singleton
    fun provideMovieDetailRepositoryRoom(movieDao: MovieDao): IMovieDetailRepository{
        return MovieDetailRepositoryRoomImpl(movieDao)
    }

}