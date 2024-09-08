package com.ukejee.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.ukejee.movieapp.ui.movies.MoviesViewModel
import com.ukejee.movieapp.ui.navigation.BottomNavigationBar
import com.ukejee.movieapp.ui.navigation.RootNavigationGraph
import com.ukejee.movieapp.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var  viewModel: MoviesViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        enableEdgeToEdge()
        setContent {
            MovieAppTheme {
                val appBarState = viewModel.appBarState.collectAsState().value
                val navController = rememberNavController()
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = appBarState.title) },
                            navigationIcon = {
                                if (appBarState.showBackButton) {
                                    IconButton(
                                        onClick = {
                                            viewModel.onBackButtonPressed()
                                            navController.popBackStack()
                                        }
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(64.dp),
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = null
                                        )
                                    }
                                }
                            }
                        )
                    },
                    bottomBar = {
                        if (appBarState.showBottomNavBar) {
                            BottomNavigationBar(navController) { screen ->
                                viewModel.onBottomNavBarClicked(screen)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    RootNavigationGraph(
                        navController = navController,
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MovieAppTheme {
        Greeting("Android")
    }
}