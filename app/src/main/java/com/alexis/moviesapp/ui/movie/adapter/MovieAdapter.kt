package com.alexis.moviesapp.ui.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexis.moviesapp.R
import com.alexis.moviesapp.domain.model.Movie

class MovieAdapter(
    private var oldListMovies: List<Movie> = listOf(),
    private val onItemClickSelected: (Int) -> Unit
) : RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        )
    }

    override fun getItemCount(): Int = oldListMovies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.setView(oldListMovies[position], onItemClickSelected)
    }

    fun updateListMovies(newListMovies: List<Movie>) {
        val movieDiff = MovieDiffUtil(oldListMovies, newListMovies)
        val result = DiffUtil.calculateDiff(movieDiff)
        oldListMovies = newListMovies
        result.dispatchUpdatesTo(this)
    }
}