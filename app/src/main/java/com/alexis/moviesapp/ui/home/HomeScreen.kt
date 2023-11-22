package com.alexis.moviesapp.ui.home

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.alexis.moviesapp.ui.core.ResultState
import com.alexis.moviesapp.ui.core.ShowCircularIndicator
import com.alexis.moviesapp.ui.core.ShowErrorScreen
import com.alexis.moviesapp.ui.movie.MovieViewModel
import com.alexis.moviesapp.ui.movie.ShowMovies
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi

@Composable
fun GetMovies(navHostController: NavHostController, viewModel: MovieViewModel) {
    ObserverStateMovies(navHostController, viewModel)
    viewModel.getMovies()
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ObserverStateMovies(navHostController: NavHostController, viewModel: MovieViewModel) {
    val state =
        viewModel.state.collectAsStateWithLifecycle().value
    when (state) {
        ResultState.Loading -> {
            ShowCircularIndicator()
        }

        is ResultState.Success -> {
            ShowMovies(
                navHostController,
                state.data
            )
        }

        is ResultState.Failure -> {
            ShowErrorScreen(
                state.exception.message.toString(),
                state.exception.cause.toString()
            )
        }
    }
}