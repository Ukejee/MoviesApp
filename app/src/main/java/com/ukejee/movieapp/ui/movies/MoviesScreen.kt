package com.ukejee.movieapp.ui.movies

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ukejee.movieapp.data.Status
import com.ukejee.movieapp.ui.theme.MovieAppTheme

@Composable
fun MoviesScreen(viewModel: MoviesViewModel, onClick: () -> Unit) {

    val popularMovies = viewModel.popularMovies.collectAsState().value
    when(popularMovies.status) {
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
            Message("No Popular Movies Found..")
        }
        Status.ERROR -> {
            popularMovies.message?.let { Message(it) }
        }
        Status.SUCCESS -> {
            LazyColumn {
                items(popularMovies.data!!) {item ->
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


@Composable
fun MovieListItem(
    title: String,
    description: String,
    imageUrl: String?,
    releaseDate: String,
    ageRating: String,
    ratings: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick() })
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Card(modifier = Modifier
                .height(150.dp)
                .padding(top = 16.dp, start = 8.dp)
                .fillMaxWidth(0.3f),
                shape = RoundedCornerShape(0.dp),
            ){
                Column(
                    modifier = Modifier.background(Color.Red),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                    )
                }

            }

            Column(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()) {
                Text(
                    text = title,
                    maxLines = 1,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = description,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(text = releaseDate)

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = ratings)

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = ageRating)

            }
        }
    }
}

@Composable
fun Message(message: String) {
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.wrapContentSize(Alignment.Center),
            text = message,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MovieListItemPreview() {
    MovieAppTheme {
        MovieListItem(
            title = "Deadpool & Wolverine",
            description = "A listless Wade Wilson toils away in civilian life with his days as the morally flexible mercenary, Deadpool, behind him. But when his homeworld faces an existential threat, Wade must reluctantly suit-up again with an even more reluctant Wolverine.",
            imageUrl = null,
            releaseDate = "Released On 2024/04/12",
            ageRating = "18+",
            ratings = "Rated 7.7/10"
        ) { }
    }
}