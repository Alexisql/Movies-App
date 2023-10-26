package com.alexis.moviesapp.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.alexis.moviesapp.databinding.ActivityMainBinding
import com.alexis.moviesapp.ui.core.ResultState
import com.alexis.moviesapp.ui.movie.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showSplash()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun showSplash() {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.state.value == ResultState.Loading
            }
        }
    }

    private fun initUI() {
        val navHost =
            supportFragmentManager.findFragmentById(binding.navHostFragmentContainer.id) as NavHostFragment
        navController = navHost.navController
        binding.bottomNavigationMenu.setupWithNavController(navController)
    }

}