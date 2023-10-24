package com.alexis.moviesapp.ui.movie.bookmarked

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexis.moviesapp.domain.model.Movie
import com.alexis.moviesapp.domain.repository.IMovieRepository
import com.alexis.moviesapp.ui.core.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkedViewModel @Inject constructor(
    private val movieRepository: IMovieRepository,
    private val dispatcherIO: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow<ResultState<List<Movie>>>(ResultState.Loading)
    val state: StateFlow<ResultState<List<Movie>>> = _state

    fun getMovies() {
        viewModelScope.launch {
            val flowMovie = movieRepository.getMovies()
            flowMovie.catch { _state.value = ResultState.Failure(it) }
                .flowOn(dispatcherIO)
                .collect { movies ->
                    _state.value = ResultState.Success(movies)
                }
        }
    }
}