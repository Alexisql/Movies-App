package com.alexis.moviesapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.alexis.moviesapp.databinding.ActivityMainBinding
import com.alexis.moviesapp.domain.model.Movie
import com.alexis.moviesapp.ui.core.ResultState
import com.alexis.moviesapp.ui.core.showToastShort
import com.alexis.moviesapp.ui.core.visibilityGone
import com.alexis.moviesapp.ui.core.visibilityVisible
import com.alexis.moviesapp.ui.movie.MovieViewModel
import com.alexis.moviesapp.ui.movie.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val movieViewModel: MovieViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        movieViewModel.getMovies()
    }

    private fun initUI() {
        initRecyclerView()
        initUIState()
    }

    private fun initRecyclerView() {
        movieAdapter = MovieAdapter()
        binding.rvMovies.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = movieAdapter
        }
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieViewModel.state.collect() {
                    val progressBar = binding.pbMovie
                    when (it) {
                        ResultState.Loading -> progressBar.visibilityVisible()
                        is ResultState.Success -> successState(it.data)
                        is ResultState.Failure -> failureState(it.exception)
                    }
                    if(it != ResultState.Loading){
                        progressBar.visibilityGone()
                    }
                }
            }
        }
    }

    private fun successState(movies: List<Movie>) {
        movieAdapter.updateListMovies(movies)
    }

    private fun failureState(exception: Throwable) {
        showToastShort(exception.message.orEmpty())
    }
}