package com.ukejee.movieapp.data.datasource

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ukejee.movieapp.data.cache.AppDatabase
import com.ukejee.movieapp.data.cache.dao.MovieDao
import com.ukejee.movieapp.data.cache.model.PersistedMovie
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MovieRoomDataSourceTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var movieDao: MovieDao
    private lateinit var db: AppDatabase


    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        movieDao = db.getMovieDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun fromMovieRoomDataSource_inputOneEntry_returnsSameEntry() = runBlocking {
        val movie = PersistedMovie(
            title = "title",
            description = "descrription",
            ratings = "ratings",
            isRatedR = true,
            releaseDate = "releaseDate",
            imageUrl = "imageUrl"
        )

        val sut = MovieRoomDataSource(db)
        sut.saveMovie(movie)
        val dbMovies = sut.getMovies()

        Assert.assertEquals(1, dbMovies.size)
        Assert.assertEquals(movie.title, dbMovies.first().title)
    }
}