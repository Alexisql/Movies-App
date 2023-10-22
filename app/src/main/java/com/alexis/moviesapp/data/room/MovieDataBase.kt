package com.alexis.moviesapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexis.moviesapp.data.room.dao.MovieDao
import com.alexis.moviesapp.data.room.entities.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieDataBase: RoomDatabase() {
    abstract fun getMovieDao():MovieDao
}