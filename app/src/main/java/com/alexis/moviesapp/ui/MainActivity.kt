package com.alexis.moviesapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.alexis.moviesapp.databinding.ActivityMainBinding
import com.alexis.moviesapp.domain.model.Movie
import com.alexis.moviesapp.ui.movie.adapter.MovieAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val listMovies: List<Movie> = listOf(
            Movie(
                565770,
                "Blue Beetle",
                "Recent college",
                "/mXLOHHc1Zeuwsl4xYKjKh2280oL.jpg",
                "/mXLOHHc1Zeuwsl4xYKjKh2280oL.jpg",
                "2023-08-16"
            ),
            Movie(
                762430,
                "Retribution",
                "Recent college",
                "/mXLOHHc1Zeuwsl4xYKjKh2280oL.jpg",
                "/oUmmY7QWWn7OhKlcPOnirHJpP1F.jpg",
                "2023-08-23"
            )
        )
        binding.rvMovies.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = MovieAdapter(listMovies)
        }
    }
}