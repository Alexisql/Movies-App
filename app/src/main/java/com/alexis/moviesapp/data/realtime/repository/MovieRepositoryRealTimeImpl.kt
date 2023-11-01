package com.alexis.moviesapp.data.realtime.repository

import com.alexis.moviesapp.data.core.Constants.MOVIE_ADDED
import com.alexis.moviesapp.data.core.Constants.MOVIE_DELETED
import com.alexis.moviesapp.data.realtime.response.MovieRealTimeResponse
import com.alexis.moviesapp.data.realtime.response.toData
import com.alexis.moviesapp.data.realtime.response.toDomain
import com.alexis.moviesapp.domain.model.Movie
import com.alexis.moviesapp.domain.repository.IMovieRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class MovieRepositoryRealTimeImpl @Inject constructor(
    private val dataBase: DatabaseReference
) : IMovieRepository {

    override fun getMovies(): Flow<Result<List<Movie>>> {
        return callbackFlow {
            val listener = dataBase.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val moviesResponse = snapshot.children.mapNotNull {
                        it.getValue(MovieRealTimeResponse::class.java)
                    }
                    trySend(Result.success(moviesResponse.map { it.toDomain() }))
                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(Result.failure(error.toException()))
                    close(error.toException())
                }
            })
            awaitClose { dataBase.removeEventListener(listener) }
        }
    }

    override fun addMovie(movie: Movie): Flow<Result<String>> {
        return callbackFlow {
            val response = dataBase.child(movie.id.toString()).setValue(movie.toData())
            response.addOnSuccessListener {
                trySend(Result.success(MOVIE_ADDED))
            }.addOnFailureListener {
                trySend(Result.failure(it))
            }
            awaitClose { close() }
        }
    }

    override fun deleteMovie(idMovie: Int): Flow<Result<String>> {
        return callbackFlow {
            val response = dataBase.child(idMovie.toString()).removeValue()
            response.addOnSuccessListener {
                trySend(Result.success(MOVIE_DELETED))
            }.addOnFailureListener {
                trySend(Result.failure(it))
            }
            awaitClose { close() }
        }
    }
}