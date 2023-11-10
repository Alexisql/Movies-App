package com.alexis.moviesapp.ui.movie.detail

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexis.moviesapp.R
import com.alexis.moviesapp.domain.model.Movie
import com.alexis.moviesapp.domain.model.MovieDetail
import com.alexis.moviesapp.ui.core.LoadImage
import com.alexis.moviesapp.ui.core.ResultState
import com.alexis.moviesapp.ui.core.ShowCircularIndicator
import com.alexis.moviesapp.ui.core.ShowErrorScreen
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi

@ExperimentalGlideComposeApi
@Composable
fun InitStateDetailMovie(movieDetailViewModel: MovieDetailViewModel) {
    var isBookmarked by rememberSaveable { mutableStateOf(false) }
    val uiSate by produceState<ResultState<MovieDetail>>(
        initialValue = ResultState.Loading,
        key1 = movieDetailViewModel
    ) {
        movieDetailViewModel.state.collect {
            value = it
        }
    }

    when (uiSate) {
        ResultState.Loading -> ShowCircularIndicator()
        is ResultState.Success -> {
            val response = (uiSate as ResultState.Success<MovieDetail>).data
            isBookmarked = response.isBookmarked
            DetailMovieScreen(response.movie)
        }
        is ResultState.Failure -> {
            val exception = (uiSate as ResultState.Failure).exception
            ShowErrorScreen(exception.message.toString(), exception.cause.toString())
        }
    }
}

@ExperimentalGlideComposeApi
@Composable
fun DetailMovieScreen(movie: Movie) {
    Column(Modifier.fillMaxSize()) {
        LoadImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            path = movie.backdropPathFull,
            contentDescription = stringResource(R.string.contentDescriptionBackdropMovie)
        )
        TextDetailMovie(
            text = movie.originalTitle,
            fontSize = 24,
            fontWeight = FontWeight.Bold
        )
        TextDetailMovie(text = movie.releaseDate)
        TextDetailMovie(text = movie.overview)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AddFloatingButton(
            imageVector = Icons.Outlined.ArrowBack,
            contentDescription = R.string.contentDescriptionBack
        )

        AddFloatingButton(
            imageVector = Icons.Outlined.FavoriteBorder,
            contentDescription = R.string.contentDescriptionBookmarked
        )
    }
}

@Composable
fun AddFloatingButton(
    imageVector: ImageVector,
    @StringRes contentDescription: Int
) {
    FloatingActionButton(onClick = { }) {
        Icon(
            imageVector = imageVector,
            contentDescription = stringResource(contentDescription)
        )
    }
}

@Composable
fun TextDetailMovie(text: String, fontSize: Int = 18, fontWeight: FontWeight = FontWeight.Normal) {
    Text(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
        text = text,
        fontSize = fontSize.sp,
        fontWeight = fontWeight
    )
}

