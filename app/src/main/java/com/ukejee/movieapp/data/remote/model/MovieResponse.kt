package com.ukejee.movieapp.data.remote.model

import com.google.gson.annotations.SerializedName
import com.ukejee.movieapp.data.remote.ApiConstants
import com.ukejee.movieapp.domain.model.Movie

data class MovieResponse(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Int>?,
    val id: Int?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val title: String?,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
) {
    fun toDomainModel(): Movie = Movie(
        title = originalTitle,
        description = overview,
        releaseDate = releaseDate,
        ratings = voteAverage.toString(),
        isRatedR = adult,
        imageUrl = posterPath?.let { ApiConstants.BASE_MOVIE_IMAGE_ENDPOINT.plus(posterPath) },
        language = originalLanguage
    )
}