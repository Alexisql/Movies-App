package com.alexis.moviesapp.ui.movie.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alexis.moviesapp.databinding.ItemMovieBinding
import com.alexis.moviesapp.domain.model.Movie
import com.alexis.moviesapp.ui.core.loadImage

class MovieViewHolder(view: View) : ViewHolder(view) {

    private val binding = ItemMovieBinding.bind(view)

    fun setView(movie: Movie, onItemClickSelected: (Int) -> Unit) {
        binding.apply {
            tvTitleItemMovie.text = movie.originalTitle
            tvDateItemMovie.text = movie.releaseDate
        }
        val posterMovie: ImageView = binding.ivPosterItemMovie
        with(posterMovie) {
            loadImage(binding.root.context, movie.posterPathFull, 30)
            setOnClickListener {
                onItemClickSelected(movie.id)
            }
        }
    }
}