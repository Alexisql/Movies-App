package com.alexis.moviesapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.alexis.moviesapp.R
import com.alexis.moviesapp.ui.core.Screen
import com.alexis.moviesapp.ui.core.navigateSingleTopTo

@Composable
fun BottomNavigationScreen(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar {
        NavigationBarItem(
            selected = currentDestination?.hierarchy?.any { it.route == Screen.Home.route } == true,
            onClick = {
                navController.navigateSingleTopTo(Screen.Home.route)
            },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = "Home"
                )
            },
            label = { Text(text = stringResource(id = R.string.home)) }
        )
        NavigationBarItem(
            selected = currentDestination?.hierarchy?.any { it.route == Screen.Bookmarked.route } == true,
            onClick = {
                navController.navigateSingleTopTo(Screen.Bookmarked.route)
            },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Favorite,
                    contentDescription = "Favorite"
                )
            },
            label = { Text(text = stringResource(id = R.string.bookmarked)) }
        )
    }
}