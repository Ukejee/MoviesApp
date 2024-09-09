package com.ukejee.movieapp.ui.movies.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ukejee.movieapp.data.Status
import com.ukejee.movieapp.ui.movies.viewmodels.MoviesViewModel

@Composable
fun FavouriteMoviesScreen(viewModel: MoviesViewModel, onClick: () -> Unit) {
    viewModel.getFavouritesMovies()
    val favouriteMovies = viewModel.favouriteMovies.collectAsState().value

    when(favouriteMovies.status) {
        Status.LOADING -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        Status.EMPTY -> {
            Message("You haven't saved any movies yet...")
        }
        Status.ERROR -> {
            favouriteMovies.message?.let { Message(it) }
        }
        Status.SUCCESS -> {
            LazyColumn {
                items(favouriteMovies.data!!) { item ->
                    MovieListItem(
                        item.title,
                        item.description,
                        item.imageUrl ?: "",
                        item.releaseDate,
                        item.ageRating,
                        item.ratings,
                        onClick = {
                            viewModel.onMovieSelected(item)
                            onClick()
                        }
                    )
                }
            }
        }
    }
}