package com.alexis.moviesapp.ui.core.fragment

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.alexis.moviesapp.domain.model.Movie
import com.alexis.moviesapp.ui.core.ResultState
import com.alexis.moviesapp.ui.core.visibilityGone
import com.alexis.moviesapp.ui.core.visibilityVisible
import com.alexis.moviesapp.ui.movie.adapter.MovieAdapter
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseFragment : Fragment() {

    private lateinit var progressBar: ProgressBar
    protected lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStateUI()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        setupViews()
        progressBar = getProgressBar()
    }

    private fun initStateUI() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                getState().collect {
                    when (it) {
                        ResultState.Loading -> progressBar.visibilityVisible()
                        is ResultState.Success -> {
                            successState(it.data)
                        }
                        is ResultState.Failure -> {
                            failureState(
                                it.exception.message.orEmpty(),
                                it.exception.cause.toString()
                            )
                        }
                    }
                    if(it != ResultState.Loading){
                        progressBar.visibilityGone()
                    }
                }
            }
        }
    }

    private fun successState(listMovies: List<Movie>){
        movieAdapter.updateListMovies(listMovies)
    }

    private fun failureState(message: String, cause: String) {
        findNavController().navigate(getNavDirectionsErrorFragment(message, cause))
    }

    private fun initAdapter() {
        movieAdapter = MovieAdapter(onItemClickSelected = {
            findNavController().navigate(getNavDirectionsDetailActivity(it))
        })
    }

    abstract fun setupViews()
    abstract fun getState(): StateFlow<ResultState<List<Movie>>>
    abstract fun getProgressBar(): ProgressBar
    abstract fun getNavDirectionsErrorFragment(message: String, cause: String): NavDirections
    abstract fun getNavDirectionsDetailActivity(idMovie: Int): NavDirections

}
