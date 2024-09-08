package com.ukejee.movieapp.domain.repository

import com.ukejee.movieapp.data.DataSource
import com.ukejee.movieapp.domain.model.Movie
import com.ukejee.movieapp.ui.movies.model.UIMovie
import kotlinx.coroutines.flow.Flow

interface AppRepositoryContract {

    suspend fun getPopularMovies() : Flow<DataSource<List<Movie>>>

    suspend fun getFavouriteMovies() : Flow<DataSource<List<Movie>>>

    suspend fun saveMovie(movie: UIMovie)

    suspend fun deleteMovie(title: String)
}