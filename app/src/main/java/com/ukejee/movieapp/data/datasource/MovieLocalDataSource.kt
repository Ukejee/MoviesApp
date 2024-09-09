package com.ukejee.movieapp.data.datasource

import com.ukejee.movieapp.data.cache.model.PersistedMovie

interface MovieLocalDataSource {

    suspend fun saveMovies(movies: List<PersistedMovie>)

    suspend fun saveMovie(movie: PersistedMovie)

    suspend fun getMovies(): List<PersistedMovie>

    suspend fun removeAllMovies()

    suspend fun removeMovie(title: String)
}