package com.ukejee.movieapp.ui.movies.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ukejee.movieapp.data.DataSource
import com.ukejee.movieapp.domain.model.Movie
import com.ukejee.movieapp.testHelpers.FakeSuccessRepository
import com.ukejee.movieapp.testHelpers.MainCoroutineScopeRule
import com.ukejee.movieapp.ui.movies.model.UIMovie
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesViewModelTest {

    private val fakeSuccessRepository = FakeSuccessRepository()

    // SUT -> System Under Test
    private lateinit var viewModel: MoviesViewModel

    @get:Rule
    val testInstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineScopeRule()

    @Before
    fun setUp() {
        viewModel = MoviesViewModel(fakeSuccessRepository)
    }

    @Test
    fun `get popular movies first emit should have a loading status`() = runTest {
        val list: MutableList<DataSource<List<UIMovie>>> = mutableListOf()

        viewModel.popularMovies.first {
            list.add(it)
        }

        viewModel.getPopularMovies()

        val expected = DataSource.loading(null)
        assertEquals(expected.status, list.first().status)
    }

    @Test
    fun `get popular movies should emit a correct DataSource when the app repository returns a successful response`() = runTest {
        val list = viewModel.popularMovies.take(2).toList()

        viewModel.getPopularMovies()

        val expected = listOf(
            DataSource.loading(null),
            DataSource.success(
                listOf(
                    Movie(
                        title = "title",
                        description = "description",
                        releaseDate = "releaseDate",
                        ratings = "ratings",
                        isRatedR = true,
                        imageUrl = "imageUrl",
                        language = ""
                    )
                ).map { it.toUiModel() }
            ),
        )
        assertEquals(expected, list)
    }

}
