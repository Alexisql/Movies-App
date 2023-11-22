package com.alexis.moviesapp.ui.movie.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.alexis.moviesapp.R
import com.alexis.moviesapp.ui.core.ResultState
import com.alexis.moviesapp.ui.core.ShowCircularIndicator
import com.alexis.moviesapp.ui.core.ShowErrorScreen
import com.alexis.moviesapp.ui.core.showToastShort
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi

@ExperimentalGlideComposeApi
@Composable
fun ObserverStateDetailMovie(
    navController : NavHostController,
    detailMovieViewModel: MovieDetailViewModel,
    movieAddOrDeleteViewModel: MovieAddOrDeleteViewModel
) {
    val stateDetailMovie =
        detailMovieViewModel.state.collectAsStateWithLifecycle().value

    when (stateDetailMovie) {
        ResultState.Loading -> {
            ShowCircularIndicator()
        }

        is ResultState.Success -> {
            ShowDetailMovie(
                navController = navController,
                movieDetail = stateDetailMovie.data,
                movieAddOrDeleteViewModel = movieAddOrDeleteViewModel
            )
            ObserverStateAddOrDelete(movieAddOrDeleteViewModel)
        }

        is ResultState.Failure -> {
            val exception = stateDetailMovie.exception
            ShowErrorScreen(exception.message.toString(), exception.cause.toString())
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AddFloatingButton(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = R.string.contentDescriptionBack
                ) { }
            }
        }
    }
}

@Composable
fun ObserverStateAddOrDelete(viewModel: MovieAddOrDeleteViewModel) {
    val state =
        viewModel.state.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    when (state) {
        ResultState.Loading -> {}

        is ResultState.Success -> {
            context.showToastShort(state.data)
        }

        is ResultState.Failure -> {
            ShowErrorScreen(
                state.exception.message.toString(),
                state.exception.cause.toString()
            )
        }
    }
}
