package com.ukejee.movieapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ukejee.movieapp.ui.movies.MovieDetailsScreen
import com.ukejee.movieapp.ui.screens.FavouritesScreen
import com.ukejee.movieapp.ui.movies.MoviesScreen
import com.ukejee.movieapp.ui.movies.MoviesViewModel

@Composable
fun RootNavigationGraph(viewModel: MoviesViewModel, navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            MoviesScreen(viewModel) {
                navController.navigate(Screen.Details.route) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                }
            }
        }
        composable(Screen.Favourites.route) {
            FavouritesScreen(viewModel) {
                navController.navigate(Screen.Details.route) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                }
            }
        }
        composable(Screen.Details.route) {
            MovieDetailsScreen(viewModel) {
                viewModel.onLikeButtonPressed(it)
            }
        }
    }
}


sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    data object Home : Screen(route = "home", label = "Home", icon = Icons.Default.Home)
    data object Favourites : Screen(route = "favourites", label = "Favourites", icon = Icons.Default.Favorite)
    data object Details : Screen(route = "details", label = "Movie Details", icon = Icons.Default.Call)
}