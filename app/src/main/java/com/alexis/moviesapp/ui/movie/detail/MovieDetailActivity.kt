package com.alexis.moviesapp.ui.movie.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.navArgs
import com.alexis.moviesapp.domain.model.MovieDetail
import com.alexis.moviesapp.ui.core.ResultState
import com.alexis.moviesapp.ui.core.ShowCircularIndicator
import com.alexis.moviesapp.ui.core.ShowErrorScreen
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalGlideComposeApi
@AndroidEntryPoint
class MovieDetailActivity : ComponentActivity() {

    private val detailMovieViewModel by viewModels<MovieDetailViewModel>()
    private val movieDetailActivityArgs by navArgs<MovieDetailActivityArgs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val stateDetailMovie =
                    detailMovieViewModel.state.collectAsStateWithLifecycle().value
                ObserverStateDetailMovie(stateDetailMovie = stateDetailMovie)
            }
        }
        detailMovieViewModel.getMovie(movieDetailActivityArgs.idMovie)
    }

    @ExperimentalGlideComposeApi
    @Composable
    fun ObserverStateDetailMovie(
        stateDetailMovie: ResultState<MovieDetail>
    ) {
        when (stateDetailMovie) {
            ResultState.Loading -> {
                ShowCircularIndicator()
            }

            is ResultState.Success -> {
                val response = stateDetailMovie.data
                DetailMovieScreen(
                    isBookmarked = response.isBookmarked,
                    movie = response.movie,
                    movieDetailViewModel = detailMovieViewModel
                )
            }

            is ResultState.Failure -> {
                val exception = stateDetailMovie.exception
                ShowErrorScreen(exception.message.toString(), exception.cause.toString())
            }
        }
    }
}