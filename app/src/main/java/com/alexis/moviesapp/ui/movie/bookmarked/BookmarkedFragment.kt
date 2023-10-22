package com.alexis.moviesapp.ui.movie.bookmarked

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.GridLayoutManager
import com.alexis.moviesapp.databinding.FragmentBookmarkedBinding
import com.alexis.moviesapp.domain.model.Movie
import com.alexis.moviesapp.ui.core.ResultState
import com.alexis.moviesapp.ui.core.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow

@AndroidEntryPoint
class BookmarkedFragment : BaseFragment() {

    private lateinit var _binding: FragmentBookmarkedBinding
    private val binding get() = _binding
    private val bookmarkedViewModel by viewModels<BookmarkedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkedBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun setupViews() {
        initRecycler()
        bookmarkedViewModel.getMovies()
    }

    override fun getState(): StateFlow<ResultState<List<Movie>>> {
        return bookmarkedViewModel.state
    }

    override fun getProgressBar(): ProgressBar {
        return binding.pbMovieBookmarked
    }

    override fun getNavDirectionsErrorFragment(message: String, cause: String): NavDirections {
        return BookmarkedFragmentDirections.actionToErrorFragment(message, cause)
    }

    override fun getNavDirectionsDetailActivity(idMovie: Int): NavDirections {
        return BookmarkedFragmentDirections.actionToMovieDetailActivity(idMovie)
    }

    private fun initRecycler() {
        binding.rvMoviesBookmarked.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = movieAdapter
        }
    }

}