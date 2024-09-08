package com.ukejee.movieapp.domain.repository

import android.util.Log
import com.ukejee.movieapp.data.DataSource
import com.ukejee.movieapp.data.cache.model.PersistedMovie
import com.ukejee.movieapp.data.datasource.MovieLocalDataSource
import com.ukejee.movieapp.data.datasource.MovieRemoteDataSource
import com.ukejee.movieapp.domain.model.Movie
import com.ukejee.movieapp.ui.movies.model.UIMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource
) : AppRepositoryContract {

    override suspend fun getPopularMovies() : Flow<DataSource<List<Movie>>> {
        return flow {
            try {
                val result = movieRemoteDataSource.getPopularMovies()
                if(result.movieResponses.isEmpty()) {
                    emit(DataSource.empty())
                } else {
                    emit(DataSource.success(result.movieResponses.map { it.toDomainModel() }))
                }
            } catch (e: Exception) {
                if(e is IOException) {
                    emit(DataSource.error("No Internet Connection", null))
                } else {
                    emit(DataSource.error("Something went wrong...", null))
                }
            }
        }
    }

    override suspend fun getFavouriteMovies(): Flow<DataSource<List<Movie>>> {
        return flow {
            try {
                val movies = movieLocalDataSource.getMovies()
                if (movies.isEmpty()) {
                    emit(DataSource.empty())
                } else {
                    emit(DataSource.success(movies.map { it.toDomain() }))
                }
            } catch (e: Exception) {
                Log.d("DB", "${e.message}")
                if(e is IOException) {
                    emit(DataSource.error("No Internet Connection", null))
                } else {
                    Log.d("DB", "${e.message}")
                    emit(DataSource.error("Something went wrong...", null))
                }
            }
        }
    }

    override suspend fun saveMovie(movie: UIMovie) {
       movieLocalDataSource.saveMovie(PersistedMovie(
           title = movie.title,
           description = movie.description,
           releaseDate = movie.releaseDate,
           ratings = movie.ratings,
           isRatedR = movie.ageRating.contains("18"),
           imageUrl = movie.imageUrl,
           language = ""
       ))
    }

    override suspend fun deleteMovie(title: String) {
        movieLocalDataSource.removeMovie(title)
    }

}
