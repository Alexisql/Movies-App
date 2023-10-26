package com.alexis.moviesapp.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.GridLayoutManager
import com.alexis.moviesapp.databinding.FragmentMovieBinding
import com.alexis.moviesapp.domain.model.Movie
import com.alexis.moviesapp.ui.core.ResultState
import com.alexis.moviesapp.ui.core.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow

@AndroidEntryPoint
class MovieFragment : BaseFragment() {

    private val movieViewModel: MovieViewModel by activityViewModels()
    private lateinit var _binding: FragmentMovieBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun setupViews() {
        initRecycler()
        movieViewModel.getMovies()
    }

    override fun getState(): StateFlow<ResultState<List<Movie>>> {
        return movieViewModel.state
    }

    override fun getProgressBar(): ProgressBar {
        return binding.pbMovie
    }

    override fun getNavDirectionsErrorFragment(message: String, cause: String): NavDirections {
        return MovieFragmentDirections.actionToErrorFragment(message, cause)
    }

    override fun getNavDirectionsDetailActivity(idMovie: Int): NavDirections {
        return MovieFragmentDirections.actionToMovieDetailActivity(idMovie)
    }

    private fun initRecycler() {
        binding.rvMovies.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = movieAdapter
        }
    }
}