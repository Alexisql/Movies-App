package com.alexis.moviesapp.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexis.moviesapp.ui.core.ResultState
import com.alexis.moviesapp.ui.core.ShowCircularIndicator
import com.alexis.moviesapp.ui.core.ShowErrorScreen
import com.alexis.moviesapp.ui.movie.MovieViewModel
import com.alexis.moviesapp.ui.movie.ShowMovies
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showSplash()
        setContent {
            ObserverStateMovies()
        }
        viewModel.getMovies()
    }

    private fun showSplash() {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.state.value == ResultState.Loading
            }
        }
    }

    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    fun ObserverStateMovies() {
        val state =
            viewModel.state.collectAsStateWithLifecycle().value
        when (state) {
            ResultState.Loading -> {
                ShowCircularIndicator()
            }

            is ResultState.Success -> {
                ShowMovies(state.data)
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