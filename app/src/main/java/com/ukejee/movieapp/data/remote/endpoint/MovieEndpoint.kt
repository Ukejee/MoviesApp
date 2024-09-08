package com.ukejee.movieapp.data.remote.endpoint

import com.ukejee.movieapp.data.remote.model.FetchPopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieEndpoint {

    @GET("movie/popular")
    suspend fun fetchPopularMovies(
        @Query("page") page: Int = 1
    ): FetchPopularMoviesResponse
}