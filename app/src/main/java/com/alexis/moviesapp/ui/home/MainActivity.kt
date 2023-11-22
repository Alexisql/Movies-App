package com.alexis.moviesapp.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alexis.moviesapp.ui.core.ResultState
import com.alexis.moviesapp.ui.core.Screen
import com.alexis.moviesapp.ui.movie.GetMovies
import com.alexis.moviesapp.ui.movie.MovieViewModel
import com.alexis.moviesapp.ui.movie.bookmarked.BookmarkedViewModel
import com.alexis.moviesapp.ui.movie.detail.GetDetailMovie
import com.alexis.moviesapp.ui.navigation.BottomNavigationScreen
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModelHome: MovieViewModel by viewModels()
    private val viewModelBookmarked: BookmarkedViewModel by viewModels()

    @OptIn(ExperimentalGlideComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showSplash()
        setContent {
            val navHostController = rememberNavController()
            Scaffold(
                bottomBar = { BottomNavigationScreen(navHostController) }
            ) { innerPadding ->
                NavHost(
                    navController = navHostController,
                    startDestination = Screen.Home.route,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(Screen.Home.route) {
                        GetMovies(navHostController, viewModelHome.state) {
                            viewModelHome.getMovies()
                        }
                    }
                    composable(Screen.Bookmarked.route) {
                        GetMovies(navHostController, viewModelBookmarked.state) {
                            viewModelBookmarked.getMovies()
                        }
                    }
                    composable(
                        Screen.Detail.route,
                        arguments = listOf(navArgument("idMovie") {
                            type = NavType.IntType
                        })
                    ) { backStackEntry ->
                        GetDetailMovie(
                            navController = navHostController,
                            idMovie = backStackEntry.arguments?.getInt("idMovie")!!
                        )
                    }
                }

            }
        }
    }

    private fun showSplash() {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModelHome.state.value == ResultState.Loading
            }
        }
    }

}