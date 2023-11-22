package com.alexis.moviesapp.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alexis.moviesapp.ui.core.ResultState
import com.alexis.moviesapp.ui.core.Screen
import com.alexis.moviesapp.ui.movie.MovieViewModel
import com.alexis.moviesapp.ui.movie.detail.GetDetailMovie
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MovieViewModel by viewModels()

    @OptIn(ExperimentalGlideComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showSplash()
        setContent {
            val navHostController = rememberNavController()
            NavHost(navController = navHostController, startDestination = Screen.Home.route) {
                composable(Screen.Home.route) { GetMovies(navHostController, viewModel) }
                composable(
                    Screen.Detail.route,
                    arguments = listOf(navArgument("idMovie") {
                        type = NavType.IntType
                    })
                ) { backStackEntry ->
                    GetDetailMovie(backStackEntry.arguments?.getInt("idMovie")!!)
                }
            }
        }
    }

    private fun showSplash() {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.state.value == ResultState.Loading
            }
        }
    }

}