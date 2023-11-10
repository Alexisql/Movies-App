package com.alexis.moviesapp.ui.movie.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexis.moviesapp.domain.model.Movie
import com.alexis.moviesapp.ui.core.AddSpacer

@Composable
fun DetailMovieScreen(movie: Movie) {
    Column(Modifier.padding(16.dp)) {
        TextDetail(
            text = movie.originalTitle,
            fontSize = 24,
            fontWeight = FontWeight.Bold
        )
        AddSpacer(size = 4)
        TextDetail(text = movie.releaseDate)
        AddSpacer(size = 4)
        TextDetail(text = movie.overview)
    }
}

@Composable
fun TextDetail(text: String, fontSize: Int = 18, fontWeight: FontWeight = FontWeight.Normal) {
    Text(
        text = text,
        fontSize = fontSize.sp,
        fontWeight = fontWeight
    )
}

