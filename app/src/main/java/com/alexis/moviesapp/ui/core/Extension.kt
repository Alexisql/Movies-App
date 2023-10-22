package com.alexis.moviesapp.ui.core

import android.content.Context
import android.widget.ImageView
import com.alexis.moviesapp.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

fun ImageView.loadImage(context: Context, path: String, roundedCorners: Int = 1) {
    Glide
        .with(context)
        .load(path)
        .error(R.drawable.image_not_found)
        .placeholder(R.drawable.placeholder)
        .transform(MultiTransformation(CenterInside(), RoundedCorners(roundedCorners)))
        .into(this)
}