package com.ukejee.movieapp.data.datasource

import com.ukejee.movieapp.data.cache.AppDatabase
import com.ukejee.movieapp.data.cache.model.PersistedMovie
import javax.inject.Inject

class MovieRoomDataSource @Inject constructor(
    private val appDatabase: AppDatabase
) : MovieLocalDataSource {

    private val movieDao = appDatabase.getMovieDao()

    override suspend fun saveMovies(movies: List<PersistedMovie>) {
        movieDao.insertAll(movies)
    }

    override suspend fun saveMovie(movie: PersistedMovie) {
        movieDao.insertMovie(movie)
    }

    override suspend fun getMovies(): List<PersistedMovie> {
       return movieDao.getAllMovies()
    }

    override suspend fun removeAllMovies() {
        movieDao.deleteAllMovies()
    }

    override suspend fun removeMovie(title: String) {
        movieDao.deleteMovie(title)
    }


}