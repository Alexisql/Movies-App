package com.alexis.moviesapp.ui.movie.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.navigation.navArgs
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalGlideComposeApi
@AndroidEntryPoint
class MovieDetailActivity : ComponentActivity() {

    private val detailViewModel by viewModels<MovieDetailViewModel>()
    private val movieAddOrDeleteViewModel by viewModels<MovieAddOrDeleteViewModel>()
    private val movieDetailActivityArgs by navArgs<MovieDetailActivityArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                InitStateDetailMovie(detailViewModel)
            }
        }
        detailViewModel.getMovie(movieDetailActivityArgs.idMovie)
    }
}