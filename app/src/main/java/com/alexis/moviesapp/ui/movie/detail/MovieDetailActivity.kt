package com.alexis.moviesapp.ui.movie.detail

import android.os.Bundle
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navArgs
import com.alexis.moviesapp.databinding.ActivityMovieDetailBinding
import com.alexis.moviesapp.domain.model.MovieDetail
import com.alexis.moviesapp.ui.core.ResultState
import com.alexis.moviesapp.ui.core.loadImage
import com.alexis.moviesapp.ui.core.showToastShort
import com.alexis.moviesapp.ui.core.visibilityGone
import com.alexis.moviesapp.ui.core.visibilityVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private val detailViewModel by viewModels<MovieDetailViewModel>()
    private val movieAddOrDeleteViewModel by viewModels<MovieAddOrDeleteViewModel>()
    private val movieDetailActivityArgs by navArgs<MovieDetailActivityArgs>()
    private lateinit var movieDetail: MovieDetail
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        detailViewModel.getMovie(movieDetailActivityArgs.idMovie)
    }

    private fun initUI() {
        initView()
        initUIState()
        initListener()
    }

    private fun initView() {
        progressBar = binding.pbDetailMovie
    }

    private fun initListener() {
        with(binding) {
            ivBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
            ivBookmarked.setOnClickListener { addedOrDeletedMovieBD(movieDetail) }
        }
    }

    private fun addedOrDeletedMovieBD(movieDetail: MovieDetail) {
        val movie = movieDetail.movie
        val isBookmarked = movieDetail.isBookmarked
        with(movieAddOrDeleteViewModel) {
            if (isBookmarked) {
                deleteMovieDB(movie.id)
            } else {
                addMovieDB(movie)
            }
        }
        movieDetail.setIsBookmarked(!isBookmarked)
    }

    private fun initUIState() {
        lifecycleScope.launch {
            detailViewModel.state.collect {
                when (it) {
                    ResultState.Loading -> progressBar.visibilityVisible()
                    is ResultState.Success -> successState(it.data)
                    is ResultState.Failure -> showToastShort(it.exception.message.orEmpty())
                }
                if (it != ResultState.Loading) {
                    progressBar.visibilityGone()
                }
            }
        }

        lifecycleScope.launch {
            movieAddOrDeleteViewModel.state.collect {
                when (it) {
                    ResultState.Loading -> progressBar.visibilityVisible()
                    is ResultState.Success -> showToastShort(it.data)
                    is ResultState.Failure -> showToastShort(it.exception.message!!)
                }
                if (it != ResultState.Loading) {
                    progressBar.visibilityGone()
                }
            }
        }
    }

    private fun successState(movieDetailResponse: MovieDetail) {
        movieDetail = movieDetailResponse
        val movie = movieDetail.movie
        binding.apply {
            ivBackdrop.loadImage(this@MovieDetailActivity, movie.backdropPathFull)
            tvTitleDetail.text = movie.originalTitle
            tvReleaseDateDetail.text = movie.releaseDate
            tvOverviewDetail.text = movie.overview
        }
    }

}