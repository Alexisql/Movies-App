package com.alexis.moviesapp.ui.navigation

import android.util.Log
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.alexis.moviesapp.R
import com.alexis.moviesapp.ui.core.Screen

@Composable
fun BottomNavigationScreen(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    BottomNavigation {
        BottomNavigationItem(
            selected = currentDestination?.hierarchy?.any { it.route == Screen.Home.route } == true,
            onClick = {
                Log.i("TEST", "SCREEN-ONE")
                navController.navigate(Screen.Home.route)
            },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = "Home"
                )
            },
            label = { Text(text = stringResource(id = R.string.home)) }
        )
        BottomNavigationItem(
            selected = currentDestination?.hierarchy?.any { it.route == Screen.Bookmarked.route } == true,
            onClick = {
                Log.i("TEST", "SCREEN-TWO")
                navController.navigate(Screen.Bookmarked.route)
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