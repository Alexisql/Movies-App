package com.alexis.moviesapp.ui.movie.detail

import androidx.lifecycle.ViewModel
import com.alexis.moviesapp.domain.model.Movie
import com.alexis.moviesapp.ui.core.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MovieAddOrDeleteViewModel @Inject constructor() :
    ViewModel() {

    private val _state = MutableStateFlow<ResultState<String>>(ResultState.Loading)
    val state: StateFlow<ResultState<String>> = _state

    fun addMovieDB(movie: Movie) {
    }

    fun deleteMovieDB(idMovie: Int) {
    }
}