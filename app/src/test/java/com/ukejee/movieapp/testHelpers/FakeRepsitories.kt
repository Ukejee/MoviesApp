package com.ukejee.movieapp.testHelpers

import com.ukejee.movieapp.data.DataSource
import com.ukejee.movieapp.domain.model.Movie
import com.ukejee.movieapp.domain.repository.AppRepositoryContract
import com.ukejee.movieapp.ui.movies.model.UIMovie
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration.Companion.seconds

class FakeSuccessRepository() : AppRepositoryContract {
    override suspend fun getPopularMovies(): Flow<DataSource<List<Movie>>> {
        return flow {
            delay(2.seconds)
            emit(
                DataSource.success(
                    data = listOf(
                        Movie(
                            title = "title",
                            description = "description",
                            releaseDate = "releaseDate",
                            ratings = "ratings",
                            isRatedR = true,
                            imageUrl = "imageUrl",
                            language = ""
                        )
                    )
                )
            )
        }
    }

    override suspend fun getFavouriteMovies(): Flow<DataSource<List<Movie>>> {
        return flow {
            delay(2.seconds)
            emit(
                DataSource.success(
                    data = listOf(
                        Movie(
                            title = "favourite-title",
                            description = "favourite-description",
                            releaseDate = "favourite-releaseDate",
                            ratings = "favourite-ratings",
                            isRatedR = true,
                            imageUrl = "imageUrl",
                            language = ""
                        )
                    )
                )
            )
        }
    }

    override suspend fun saveMovie(movie: UIMovie) {

    }

    override suspend fun deleteMovie(title: String) {

    }

}