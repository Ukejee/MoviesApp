package com.ukejee.movieapp.ui.movies.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ukejee.movieapp.data.DataSource
import com.ukejee.movieapp.data.Status
import com.ukejee.movieapp.domain.repository.AppRepositoryContract
import com.ukejee.movieapp.ui.movies.model.UIMovie
import com.ukejee.movieapp.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val appRepository: AppRepositoryContract
) : ViewModel() {

    private val _popularMovies = MutableStateFlow<DataSource<List<UIMovie>>>(DataSource.loading(null))
    val popularMovies get() = _popularMovies

    private val _favouriteMovies = MutableStateFlow<DataSource<List<UIMovie>>>(DataSource.loading(null))
    val favouriteMovies get() = _favouriteMovies

    private val _appBarState = MutableStateFlow(AppBarState("Movies", false, true))
    val appBarState get() = _appBarState

    private val _selectedMovie = MutableStateFlow<UIMovie?>(null)
    val selectedMovie get() = _selectedMovie

    fun getPopularMovies() {
        viewModelScope.launch {
            appRepository.getPopularMovies().collect { movies ->
                when (movies.status) {
                    Status.EMPTY -> {
                        _popularMovies.emit(DataSource.empty())
                    }

                    Status.SUCCESS -> {
                        _popularMovies.emit(DataSource.success(movies.data?.map { it.toUiModel() }))
                    }

                    else -> {
                        _popularMovies.emit(DataSource.error(movies.message.toString(), null))
                    }
                }
            }
        }
    }

    fun getFavouritesMovies() {
        viewModelScope.launch {
            appRepository.getFavouriteMovies().collect { movies ->
                when (movies.status) {
                    Status.EMPTY -> {
                        _favouriteMovies.emit(DataSource.empty())
                    }

                    Status.SUCCESS -> {
                        _favouriteMovies.emit(DataSource.success(movies.data?.map { it.toUiModel() }))
                    }

                    else -> {
                        _favouriteMovies.emit(DataSource.error(movies.message.toString(), null))
                    }
                }
            }
        }
    }

    fun onBottomNavBarClicked(screen: String) {
        viewModelScope.launch {
            if (screen == "Home") {
                _appBarState.emit(AppBarState("Movies", false, true))
            } else {
                _appBarState.emit(AppBarState(screen, false, true))
            }
        }
    }

    fun onMovieSelected(movie: UIMovie) {
        viewModelScope.launch {
            _selectedMovie.emit(movie)
            _appBarState.emit(AppBarState(Screen.Details.label, true, false))
        }
    }

    fun onBackButtonPressed() {
        viewModelScope.launch {
            _appBarState.emit(AppBarState("Movies", false, true))
        }
    }

    fun onLikeButtonPressed(movie: UIMovie) {
        viewModelScope.launch {
            appRepository.saveMovie(movie)
        }
    }

    init {
        getPopularMovies()
    }
}

data class AppBarState(val title: String, val showBackButton: Boolean, val showBottomNavBar: Boolean)
