package com.ukejee.movieapp.data.datasource

import androidx.test.platform.app.InstrumentationRegistry
import com.ukejee.movieapp.application.MovieApplication
import com.ukejee.movieapp.data.remote.endpoint.MovieEndpoint
import com.ukejee.movieapp.data.remote.model.FetchPopularMoviesResponse
import com.ukejee.movieapp.data.remote.model.MovieResponse
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

class MovieRetrofitDataSourceTest {

    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(MovieEndpoint::class.java)

    private val sut = MovieRetrofitDataSource(api)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun fromGetPopularMovies_with200Response_returnsProperlyMappedObject() {
        mockWebServer.enqueueResponse("testGetPopularMoviesResponse.json", 200)

        runBlocking {
            val actual = sut.getPopularMovies()

            val expectedResponse =  FetchPopularMoviesResponse(
                page = 1,
                totalResults = 1,
                totalPages = 1,
                movieResponses = listOf(
                    MovieResponse(
                        adult = false,
                        backdropPath = "/yDHYTfA3R0jFYba16jBB1ef8oIt.jpg",
                        genreIds = listOf(28,35,878),
                        id = 533535,
                        originalLanguage = "en",
                        originalTitle = "Deadpool & Wolverine",
                        overview = "A listless Wadewhen his homeworld faceverine.",
                        popularity = 3500.919,
                        posterPath = "/8cdWjvZQUExUUTzyp4t6EDMubfO.jpg",
                        releaseDate = "2024-07-24",
                        title = "Deadpool & Wolverine",
                        video = false,
                        voteAverage = 7.752,
                        voteCount = 2692
                    )
                )

            )

            assertEquals(expectedResponse, actual)
        }
    }

    private fun MockWebServer.enqueueResponse(fileName: String, code: Int) {
        val context = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as MovieApplication
        val inputStream = context.assets.open("networkResponses/$fileName")

        val source = inputStream.let { inputStream.source().buffer() }
        enqueue(
            MockResponse()
                .setResponseCode(code)
                .setBody(source.readString(StandardCharsets.UTF_8))
        )
    }

}