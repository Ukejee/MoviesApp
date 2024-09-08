package com.ukejee.movieapp.domain.model

import com.ukejee.movieapp.ui.movies.model.UIMovie

data class Movie(
    val title: String?,
    val description: String?,
    val releaseDate: String?,
    val ratings: String?,
    val isRatedR: Boolean,
    val imageUrl: String?,
    val language: String?
) {

    fun toUiModel(): UIMovie = UIMovie(
        title = title ?: "N/A",
        description = description ?: "N/A",
        releaseDate = "Released on $releaseDate",
        ratings = "Rated $ratings/10",
        ageRating = if (isRatedR) "Suitable for ages 18 and above" else "Suitable for ages 3 and above",
        imageUrl = imageUrl
    )
}

