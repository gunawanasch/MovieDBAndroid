package com.gunawan.moviedb.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.gunawan.moviedb.core.network.NetworkResponseState
import com.gunawan.moviedb.domain.entities.MovieEntity
import com.gunawan.moviedb.domain.entities.ResultMovieItem
import com.gunawan.moviedb.domain.usecases.GetMovieUseCase
import com.gunawan.moviedb.presentation.movie.MovieUIState
import com.gunawan.moviedb.presentation.movie.MovieViewModel
import com.gunawan.moviedb.tException
import com.gunawan.moviedb.tGenreId
import com.gunawan.moviedb.tMovieEntity
import com.gunawan.moviedb.tPage
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
class MovieViewModelTest {
    private lateinit var movieViewModel: MovieViewModel
    @Mock
    private lateinit var getMovieUseCase: GetMovieUseCase
    private val expectedMovieEntity = MovieEntity(
        1,
        46261,
        mutableListOf(
            ResultMovieItem(
                "A listless Wade Wilson toils away in civilian life with his days as the morally flexible mercenary, Deadpool, behind him. But when his homeworld faces an existential threat, Wade must reluctantly suit-up again with an even more reluctant Wolverine.",
                "en",
                "Deadpool & Wolverine",
                false,
                "Deadpool & Wolverine",
                arrayListOf(28, 35, 878),
                "/8cdWjvZQUExUUTzyp4t6EDMubfO.jpg",
                "/yDHYTfA3R0jFYba16jBB1ef8oIt.jpg",
                "2024-07-24",
                2225.512,
                7.7,
                533535,
                false,
                3168
            )
        ),
        925202
    )
    private val expectedException = Exception("Internal server error")
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        movieViewModel = MovieViewModel(getMovieUseCase)
    }

    @Test
    fun `When get movie return success`() = runTest {
        Mockito.`when`(getMovieUseCase.invoke(tGenreId, tPage))
            .thenReturn(
                flow {
                    emit(NetworkResponseState.Loading)
                    emit(NetworkResponseState.Success(tMovieEntity))
                }
            )
        movieViewModel.getMovie(tGenreId, tPage)
        val state = movieViewModel.ldMovieState.value
        Truth.assertThat((state as MovieUIState.Success).result == expectedMovieEntity)
    }

    @Test
    fun `When get movie return error`() = runTest {
        Mockito.`when`(getMovieUseCase.invoke(tGenreId, tPage))
            .thenReturn(
                flow {
                    emit(NetworkResponseState.Loading)
                    emit(NetworkResponseState.Error(tException))
                }
            )
        movieViewModel.getMovie(tGenreId, tPage)
        val state = movieViewModel.ldMovieState.value
        Truth.assertThat((state as MovieUIState.Error).toString() == expectedException.toString())
    }

}