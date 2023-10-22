package com.alexis.moviesapp.data.core

object Constants {
    const val TOKEN =
        "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3ODQ3ODU4NTlkYjY2NGZhN2ZmYWJhMGYzNmQ0OGM3ZCIsInN1YiI6IjVjMzRjNDJjMGUwYTI2NTRiZWQ4YzliNSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.VAwb2dB69P72JXKo1ady1l4zp-vy1dsCS3XP-cGE6fY"
    const val AUTHORIZATION = "Authorization"
    const val BASE_URL = "https://api.themoviedb.org/3/movie/"
    const val MOVIE_DATA_BASE_NAME = "MovieAppDataBase"
    const val MOVIE_NOT_FOUND = "The movie you are trying to search for is not stored in the database"
    private const val MOVIE_NOT_COULD = "The movie could not be "
    const val MOVIE_ADDED = "Added"
    const val MOVIE_NOT_ADDED = "$MOVIE_NOT_COULD$MOVIE_ADDED"
    const val MOVIE_DELETED = "Deleted"
    const val MOVIE_NOT_DELETED = "$MOVIE_NOT_COULD$MOVIE_DELETED"
    const val RESPONSE_ERROR_ROOM = 0

}