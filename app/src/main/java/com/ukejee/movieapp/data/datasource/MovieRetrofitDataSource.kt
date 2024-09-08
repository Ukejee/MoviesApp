package com.ukejee.movieapp.data.datasource

import com.ukejee.movieapp.data.remote.endpoint.MovieEndpoint
import com.ukejee.movieapp.data.remote.model.FetchPopularMoviesResponse
import javax.inject.Inject

class MovieRetrofitDataSource @Inject constructor(
    private val movieEndpoint: MovieEndpoint
) : MovieRemoteDataSource {

    override suspend fun getPopularMovies(): FetchPopularMoviesResponse {
        return movieEndpoint.fetchPopularMovies()
    }
}