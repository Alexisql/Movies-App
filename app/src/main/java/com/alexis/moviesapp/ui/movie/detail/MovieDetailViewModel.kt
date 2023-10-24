package com.alexis.moviesapp.ui.movie.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexis.moviesapp.data.core.di.AppModule
import com.alexis.moviesapp.domain.model.MovieDetail
import com.alexis.moviesapp.domain.repository.IMovieDetailRepository
import com.alexis.moviesapp.ui.core.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    @AppModule.MovieDetailRepositoryRetrofit private val movieRepositoryRetrofit: IMovieDetailRepository,
    @AppModule.MovieDetailRepositoryRoom private val movieRepositoryRoom: IMovieDetailRepository,
    private val dispatcherIO: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow<ResultState<MovieDetail>>(ResultState.Loading)
    val state: StateFlow<ResultState<MovieDetail>> = _state

    fun getMovie(idMovie: Int) {
        viewModelScope.launch {
            withContext(dispatcherIO) {
                val response = movieRepositoryRoom.getDetailMovie(idMovie)
                response
                    .onSuccess {
                        _state.value = ResultState.Success(it)
                    }.onFailure {
                        val responseRetrofit = movieRepositoryRetrofit.getDetailMovie(idMovie)
                        responseRetrofit
                            .onSuccess {
                                _state.value = ResultState.Success(it)
                            }
                            .onFailure {
                                _state.value = ResultState.Failure(Exception(it.message))
                            }
                    }
            }
        }
    }
}