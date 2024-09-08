package com.ukejee.movieapp.data.datasource

import com.ukejee.movieapp.data.remote.model.FetchPopularMoviesResponse

interface MovieRemoteDataSource {

    suspend fun getPopularMovies(): FetchPopularMoviesResponse
}