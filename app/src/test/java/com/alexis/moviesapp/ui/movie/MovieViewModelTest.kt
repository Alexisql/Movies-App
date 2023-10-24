package com.alexis.moviesapp.ui.movie

import com.alexis.moviesapp.domain.builder.MovieBuilder
import com.alexis.moviesapp.domain.repository.IMoviePopularRepository
import com.alexis.moviesapp.ui.MainDispatcherRule
import com.alexis.moviesapp.ui.core.ResultState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var repositoryMock: IMoviePopularRepository
    private lateinit var viewModel: MovieViewModel

    @Before
    fun initBefore() {
        MockKAnnotations.init(this)
        viewModel = MovieViewModel(repositoryMock, mainDispatcherRule.testDispatcher)
    }

    @Test
    fun `get movies, validate mapping to the domain layer, successful`() =
        runTest {
            //Arrange
            val moviesFakeDomain = listOf(MovieBuilder().build())
            coEvery { repositoryMock.getMovies() } returns Result.success(moviesFakeDomain)

            //Act
            viewModel.getMovies()

            //Assert
            assertEquals(ResultState.Success(moviesFakeDomain), viewModel.state.value)
        }
}