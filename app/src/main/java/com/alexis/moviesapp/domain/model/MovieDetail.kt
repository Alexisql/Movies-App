package com.alexis.moviesapp.domain.model

data class MovieDetail(val movie: Movie, var isBookmarked: Boolean) {
    fun setIsBookmarked(isBookmarked: Boolean) {
        this.isBookmarked = isBookmarked
    }
}
