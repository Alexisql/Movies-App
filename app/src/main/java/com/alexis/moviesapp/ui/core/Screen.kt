package com.alexis.moviesapp.ui.core

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Detail : Screen("detail/{idMovie}") {
        fun createRoute(idMovie: Int) = "detail/$idMovie"
    }
}