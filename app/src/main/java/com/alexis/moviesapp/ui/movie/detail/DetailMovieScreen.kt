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
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alexis.moviesapp.R
import com.alexis.moviesapp.domain.model.MovieDetail
import com.alexis.moviesapp.ui.core.LoadImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi

@ExperimentalGlideComposeApi
@Composable
fun GetDetailMovie(
    navController: NavHostController,
    idMovie: Int,
    detailMovieViewModel: MovieDetailViewModel = hiltViewModel(),
    movieAddOrDeleteViewModel: MovieAddOrDeleteViewModel = hiltViewModel()
) {
    ObserverStateDetailMovie(navController, detailMovieViewModel, movieAddOrDeleteViewModel)
    detailMovieViewModel.getMovie(idMovie)
}

@ExperimentalGlideComposeApi
@Composable
fun ShowDetailMovie(
    navController: NavHostController,
    movieDetail: MovieDetail,
    movieAddOrDeleteViewModel: MovieAddOrDeleteViewModel
) {
    var isFavorite by rememberSaveable { mutableStateOf(movieDetail.isBookmarked) }
    val movie = movieDetail.movie
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
        ) {
            navController.popBackStack()
        }
        AddFloatingButton(
            imageVector = if (isFavorite) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
            color = if (isFavorite) Color.Red else Color.Black,
            contentDescription = R.string.contentDescriptionBookmarked
        ) {
            if (isFavorite) {
                movieAddOrDeleteViewModel.deleteMovieDB(movie.id)
            } else {
                movieAddOrDeleteViewModel.addMovieDB(movie)
            }
            isFavorite = !isFavorite
        }
    }
}

@Composable
fun AddFloatingButton(
    imageVector: ImageVector,
    color: Color = Color.Black,
    @StringRes contentDescription: Int,
    onIconClickListener: () -> Unit
) {
    FloatingActionButton(onClick = { onIconClickListener() }) {
        Icon(
            imageVector = imageVector,
            contentDescription = stringResource(contentDescription),
            tint = color
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

