package com.alexis.moviesapp.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexis.moviesapp.domain.model.Movie
import com.alexis.moviesapp.domain.repository.IMoviePopularRepository
import com.alexis.moviesapp.ui.core.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val moviePopularRepository: IMoviePopularRepository,
    private val dispatcherIO: CoroutineDispatcher
) : ViewModel() {
    private var _state = MutableStateFlow<ResultState<List<Movie>>>(ResultState.Loading)
    val state: StateFlow<ResultState<List<Movie>>> = _state

    fun getMovies() {
        viewModelScope.launch {
            withContext(dispatcherIO) {
                val moviesPopular = moviePopularRepository.getMovies()
                moviesPopular
                    .onSuccess { _state.value = ResultState.Success(it) }
                    .onFailure { _state.value = ResultState.Failure(it) }
            }
        }
    }
}