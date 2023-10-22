package com.alexis.moviesapp.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexis.moviesapp.domain.model.Movie
import com.alexis.moviesapp.domain.repository.IMoviePopularRepository
import com.alexis.moviesapp.ui.core.Constants.DISPATCHER_IO
import com.alexis.moviesapp.ui.core.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val moviePopularRepository: IMoviePopularRepository) :
    ViewModel() {

    private var _state = MutableStateFlow<ResultState<List<Movie>>>(ResultState.Loading)
    val state: StateFlow<ResultState<List<Movie>>> = _state

    fun getMovies() {
        viewModelScope.launch {
            withContext(DISPATCHER_IO) {
                try {
                    val moviesPopular = moviePopularRepository.getMovies()
                    _state.value = ResultState.Success(moviesPopular)
                } catch (exception: Throwable) {
                    _state.value = ResultState.Failure(exception)
                }
            }
        }
    }
}