package com.alexis.moviesapp.domain.builder

import com.alexis.moviesapp.domain.model.Movie

class MovieBuilder {

    private val movie = Movie(
        299054,
        "Expend4bles",
        "Armed with every weapon they can get their hands..",
        "/rMvPXy8PUjj1o8o1pzgQbdNCsvj.jpg",
        "/mOX5O6JjCUWtlYp5D8wajuQRVgy.jpg",
        "2023-09-15"
    )

    fun withId(id: Int) = apply {
        movie.id = id
    }

    fun build() = movie
}