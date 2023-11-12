package com.alexis.moviesapp.ui.movie.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.navArgs
import com.alexis.moviesapp.ui.core.ResultState
import com.alexis.moviesapp.ui.core.ShowCircularIndicator
import com.alexis.moviesapp.ui.core.ShowErrorScreen
import com.alexis.moviesapp.ui.core.showToastShort
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalGlideComposeApi
@AndroidEntryPoint
class MovieDetailActivity : ComponentActivity() {

    private val detailMovieViewModel by viewModels<MovieDetailViewModel>()
    private val detailMovieViewModelAddOrDelete by viewModels<MovieAddOrDeleteViewModel>()
    private val movieDetailActivityArgs by navArgs<MovieDetailActivityArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ObserverStateDetailMovie(detailMovieViewModelAddOrDelete)
                ObserverStateAddOrDelete(detailMovieViewModelAddOrDelete)
            }
        }
        detailMovieViewModel.getMovie(movieDetailActivityArgs.idMovie)
    }

    @ExperimentalGlideComposeApi
    @Composable
    fun ObserverStateDetailMovie(
        movieAddOrDeleteViewModel: MovieAddOrDeleteViewModel
    ) {
        val stateDetailMovie =
            detailMovieViewModel.state.collectAsStateWithLifecycle().value

        when (stateDetailMovie) {
            ResultState.Loading -> {
                ShowCircularIndicator()
            }

            is ResultState.Success -> {
                DetailMovieScreen(
                    movieDetail = stateDetailMovie.data,
                    movieAddOrDeleteViewModel = movieAddOrDeleteViewModel,
                )
            }

            is ResultState.Failure -> {
                val exception = stateDetailMovie.exception
                ShowErrorScreen(exception.message.toString(), exception.cause.toString())
            }
        }
    }

    @Composable
    fun ObserverStateAddOrDelete(
        detailMovieViewModelAddOrDelete: MovieAddOrDeleteViewModel
    ) {
        val state =
            detailMovieViewModelAddOrDelete.state.collectAsStateWithLifecycle().value
        when (state) {
            ResultState.Loading -> {}

            is ResultState.Success -> {
                this.showToastShort(state.data)
            }

            is ResultState.Failure -> {
                ShowErrorScreen(
                    state.exception.message.toString(),
                    state.exception.cause.toString()
                )
            }
        }
    }

}