package com.alexis.moviesapp.ui.movie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.alexis.moviesapp.R
import com.alexis.moviesapp.domain.model.Movie
import com.alexis.moviesapp.ui.core.LoadImage
import com.alexis.moviesapp.ui.core.ResultState
import com.alexis.moviesapp.ui.core.Screen
import com.alexis.moviesapp.ui.core.ShowCircularIndicator
import com.alexis.moviesapp.ui.core.ShowErrorScreen
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import kotlinx.coroutines.flow.StateFlow

@Composable
fun GetMovies(
    navHostController: NavHostController,
    resultState: StateFlow<ResultState<List<Movie>>>,
    onGetMovies: () -> Unit
) {
    onGetMovies()
    ObserverStateMovies(navHostController, resultState)
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ObserverStateMovies(
    navHostController: NavHostController,
    resultState: StateFlow<ResultState<List<Movie>>>
) {
    val state =
        resultState.collectAsStateWithLifecycle().value
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

@ExperimentalGlideComposeApi
@Composable
fun ShowMovies(navHostController: NavHostController, listMovies: List<Movie>) {
    LazyVerticalGrid(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        content = {
            items(listMovies) { movie ->
                ItemMovie(movie = movie, onItemSelected = {
                    navHostController.navigate(Screen.Detail.createRoute(it))
                })
            }
        })
}

@ExperimentalGlideComposeApi
@Composable
fun ItemMovie(movie: Movie, onItemSelected: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .clickable {
                onItemSelected(movie.id)
            },
        shape = RoundedCornerShape(10.dp),
        elevation = 8.dp

    ) {
        Column {
            LoadImage(
                modifier = Modifier,
                path = movie.posterPathFull,
                contentDescription = stringResource(R.string.contentDescriptionPosterMovie)
            )
            Text(
                modifier = Modifier.padding(4.dp),
                text = movie.originalTitle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier.padding(start = 4.dp, end = 4.dp, bottom = 4.dp),
                text = movie.releaseDate
            )
        }
    }
}