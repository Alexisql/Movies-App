package com.alexis.moviesapp.data.builder

import com.alexis.moviesapp.data.room.entities.MovieEntity

class MovieEntityBuilder {

    private val movieEntity = MovieEntity(
        299054,
        "Expend4bles",
        "Armed with every weapon they can get their hands..",
        "/rMvPXy8PUjj1o8o1pzgQbdNCsvj.jpg",
        "/mOX5O6JjCUWtlYp5D8wajuQRVgy.jpg",
        "2023-09-15"
    )

    fun withId(id: Int) = apply {
        movieEntity.id = id
    }

    fun build() = movieEntity
}