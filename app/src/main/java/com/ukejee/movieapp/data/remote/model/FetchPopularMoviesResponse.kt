package com.ukejee.movieapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class FetchPopularMoviesResponse(
    val page: Int,
    @SerializedName("results")
    val movieResponses: List<MovieResponse>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)