package com.ukejee.movieapp.ui.movies.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ukejee.movieapp.ui.movies.viewmodels.MoviesViewModel
import com.ukejee.movieapp.ui.movies.model.UIMovie
import com.ukejee.movieapp.ui.theme.MovieAppTheme

@Composable
fun MovieDetailsScreen(viewModel: MoviesViewModel, onClick: (movie: UIMovie) -> Unit) {
    
    val movie = viewModel.selectedMovie.collectAsState().value
    
    movie?.let {
        Details(
            title = movie.title,
            description = movie.description,
            releaseDate = movie.releaseDate,
            ratings = movie.ratings,
            ageRatings = movie.ageRating,
            imageUrl = movie.imageUrl,
            onClick = {
                onClick(it)
            }
        )
    }
}

@Composable
fun Details(
    paddingValues: PaddingValues = PaddingValues(8.dp),
    title: String,
    description: String,
    releaseDate: String,
    ratings: String,
    ageRatings: String,
    imageUrl: String?,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .padding(8.dp),
            shape = RoundedCornerShape(0.dp),
        ){
            Column(
                modifier = Modifier.background(Color.White),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = imageUrl,
                    contentDescription = null,
                )
            }
        }
        Text(
            text = title,
            fontSize = 28.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = description,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Start
            )
        }
        Row(
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = releaseDate,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Start
            )
        }
        Row(
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = ratings,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Start
            )
        }
        Row (
            modifier = Modifier.padding(top = 8.dp)
        ){
            Text(
                text = ageRatings,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Start
            )
        }

        Button(
            onClick = { onClick() },

            modifier = Modifier.padding(top = 16.dp)
                .fillMaxWidth(0.5f)
                .align(Alignment.CenterHorizontally)

        ) {
            Column {
                Text(text = "Like")
            }
        }


    }
}

@Preview
@Composable
fun DetailsPreview() {
    MovieAppTheme {
        Details(
            title = "Earth",
            description = "A listless Wade Wilson toils away in civilian life with his days as the morally flexible mercenary, Deadpool, behind him. But when his homeworld faces an existential threat, Wade must reluctantly suit-up again with an even more reluctant Wolverine",
            releaseDate = "23",
            ratings = "304",
            ageRatings = "10465",
            imageUrl = "Arid",
        ) { }
    }
}