package com.alexis.moviesapp.ui.movie.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexis.moviesapp.domain.model.Movie
import com.alexis.moviesapp.domain.repository.IMovieRepository
import com.alexis.moviesapp.ui.core.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieAddOrDeleteViewModel @Inject constructor(
    private val repository: IMovieRepository,
    private val dispatcherIO: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow<ResultState<String>>(ResultState.Loading)
    val state: StateFlow<ResultState<String>> = _state

    fun addMovieDB(movie: Movie) {
        viewModelScope.launch {
            withContext(dispatcherIO) {
                val response = repository.addMovie(movie)
                response
                    .onSuccess { _state.value = ResultState.Success(it) }
                    .onFailure { _state.value = ResultState.Failure(Exception(it.message)) }
            }
        }
    }

    fun deleteMovieDB(idMovie: Int) {
        viewModelScope.launch {
            withContext(dispatcherIO) {
                val response = repository.deleteMovie(idMovie)
                response
                    .onSuccess { _state.value = ResultState.Success(it) }
                    .onFailure { _state.value = ResultState.Failure(Exception(it.message)) }
            }
        }
    }
}