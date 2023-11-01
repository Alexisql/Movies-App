package com.alexis.moviesapp.data.realtime.repository

import com.alexis.moviesapp.data.core.Constants.MOVIE_NOT_FOUND
import com.alexis.moviesapp.data.realtime.response.MovieRealTimeResponse
import com.alexis.moviesapp.data.realtime.response.toMovieDetailDomain
import com.alexis.moviesapp.domain.model.MovieDetail
import com.alexis.moviesapp.domain.repository.IMovieDetailRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class MovieDetailRepositoryRealTimeImpl @Inject constructor(
    private val dataBase: DatabaseReference
) : IMovieDetailRepository {

    override fun getDetailMovie(idMovie: Int): Flow<Result<MovieDetail>> {
        return callbackFlow {
            val listener = dataBase.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val movieResponse = snapshot
                        .child(idMovie.toString()).getValue(MovieRealTimeResponse::class.java)

                    movieResponse?.let {
                        trySend(Result.success(movieResponse.toMovieDetailDomain()))
                    } ?: trySend(Result.failure(Exception(MOVIE_NOT_FOUND)))
                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(Result.failure(error.toException()))
                    close(error.toException())
                }
            })
            awaitClose { dataBase.removeEventListener(listener) }
        }
    }
}