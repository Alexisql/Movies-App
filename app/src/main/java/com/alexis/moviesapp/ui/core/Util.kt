package com.alexis.moviesapp.ui.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alexis.moviesapp.R
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

@Composable
fun AddSpacer(size: Int) {
    Spacer(modifier = Modifier.size(size.dp))
}

@ExperimentalGlideComposeApi
@Composable
fun LoadImage(
    modifier: Modifier,
    path: String,
    contentDescription: String,
    roundedCorners: Int = 1
) {
    GlideImage(
        modifier = modifier,
        model = path,
        contentDescription = contentDescription
    ) {
        it.error(R.drawable.image_not_found)
            .placeholder(R.drawable.placeholder)
            .transform(MultiTransformation(CenterInside(), RoundedCorners(roundedCorners)))
    }
}

@Composable
fun ShowCircularIndicator() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }

}
