package com.alexis.moviesapp.ui.movie.bookmarked

import androidx.lifecycle.ViewModel
import com.alexis.moviesapp.domain.model.Movie
import com.alexis.moviesapp.ui.core.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class BookmarkedViewModel @Inject constructor() :
    ViewModel() {

    private val _state = MutableStateFlow<ResultState<List<Movie>>>(ResultState.Loading)
    val state: StateFlow<ResultState<List<Movie>>> = _state

    fun getMovies() {
    }
}