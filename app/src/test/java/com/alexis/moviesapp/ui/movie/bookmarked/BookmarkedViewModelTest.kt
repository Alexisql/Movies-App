package com.alexis.moviesapp.ui.movie.bookmarked

import com.alexis.moviesapp.domain.builder.MovieBuilder
import com.alexis.moviesapp.domain.repository.IMovieRepository
import com.alexis.moviesapp.ui.MainDispatcherRule
import com.alexis.moviesapp.ui.core.ResultState
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class BookmarkedViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var repositoryMock: IMovieRepository
    private lateinit var viewModel: BookmarkedViewModel

    @Before
    fun initBefore() {
        MockKAnnotations.init(this)
        viewModel = BookmarkedViewModel(repositoryMock, mainDispatcherRule.testDispatcher)
    }

    @Test
    fun `get movies, validate mapping to the domain layer, failure`() =
        runTest {
            //Arrange
            val moviesFakeDomain = listOf(MovieBuilder().build())
            every { repositoryMock.getMovies() } returns flowOf(moviesFakeDomain)

            //Act
            viewModel.getMovies()

            //Assert
            assertEquals(ResultState.Success(moviesFakeDomain), viewModel.state.value)
        }
}