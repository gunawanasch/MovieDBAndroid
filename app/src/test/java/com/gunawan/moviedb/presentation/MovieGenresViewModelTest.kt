package com.gunawan.moviedb.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.gunawan.moviedb.core.network.NetworkResponseState
import com.gunawan.moviedb.domain.entities.GenresItem
import com.gunawan.moviedb.domain.entities.MovieGenresEntity
import com.gunawan.moviedb.domain.usecases.GetMovieGenresUseCase
import com.gunawan.moviedb.presentation.main.MovieGenresUIState
import com.gunawan.moviedb.presentation.main.MovieGenresViewModel
import com.gunawan.moviedb.tException
import com.gunawan.moviedb.tMovieGenresEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MovieGenresViewModelTest {
    private lateinit var movieGenresViewModel: MovieGenresViewModel
    @Mock
    private lateinit var getMovieGenresUseCase: GetMovieGenresUseCase
    private val expectedMovieGenresEntity = MovieGenresEntity(
        mutableListOf(
            GenresItem(
                "Action",
                28
            )
        )
    )
    private val expectedException = Exception("Internal server error")
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        movieGenresViewModel = MovieGenresViewModel(getMovieGenresUseCase)
    }

    @Test
    fun `When get movie genres return success`() = runTest {
        Mockito.`when`(getMovieGenresUseCase.invoke())
            .thenReturn(
                flow {
                    emit(NetworkResponseState.Loading)
                    emit(NetworkResponseState.Success(tMovieGenresEntity))
                }
            )
        movieGenresViewModel.getMovieGenres()
        val state = movieGenresViewModel.ldMovieGenresState.value
        assertThat((state as MovieGenresUIState.Success).result == expectedMovieGenresEntity)
    }

    @Test
    fun `When get movie genres return error`() = runTest {
        Mockito.`when`(getMovieGenresUseCase.invoke())
            .thenReturn(
                flow {
                    emit(NetworkResponseState.Loading)
                    emit(NetworkResponseState.Error(tException))
                }
            )
        movieGenresViewModel.getMovieGenres()
        val state = movieGenresViewModel.ldMovieGenresState.value
        assertThat((state as MovieGenresUIState.Error).toString() == expectedException.toString())
    }

}