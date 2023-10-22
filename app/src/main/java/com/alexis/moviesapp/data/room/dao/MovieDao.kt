package com.alexis.moviesapp.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alexis.moviesapp.data.room.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM Movie ORDER BY id DESC")
    fun getMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: MovieEntity): Long

    @Query("SELECT * FROM Movie WHERE id = :idMovie")
    suspend fun getDetailMovie(idMovie: Int): MovieEntity?

    @Query("DELETE FROM Movie WHERE id = :idMovie")
    suspend fun deleteMovie(idMovie: Int): Int
}