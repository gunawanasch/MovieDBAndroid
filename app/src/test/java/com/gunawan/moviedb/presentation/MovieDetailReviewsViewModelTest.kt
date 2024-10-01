package com.gunawan.moviedb.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.gunawan.moviedb.core.network.NetworkResponseState
import com.gunawan.moviedb.domain.entities.MovieDetailReviewsAuthorDetails
import com.gunawan.moviedb.domain.entities.MovieDetailReviewsEntity
import com.gunawan.moviedb.domain.entities.ResultMovieDetailReviewsItem
import com.gunawan.moviedb.domain.usecases.GetMovieDetailReviewsUseCase
import com.gunawan.moviedb.presentation.movie_detail_reviews.MovieDetailReviewsUIState
import com.gunawan.moviedb.presentation.movie_detail_reviews.MovieDetailReviewsViewModel
import com.gunawan.moviedb.tException
import com.gunawan.moviedb.tMovieDetailReviewsEntity
import com.gunawan.moviedb.tMovieIdAvengerEndGame
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
class MovieDetailReviewsViewModelTest {
    private lateinit var movieDetailReviewsViewModel: MovieDetailReviewsViewModel
    @Mock
    private lateinit var getMovieDetailReviewsUseCase: GetMovieDetailReviewsUseCase
    private val expectedMovieDetailReviewsEntity = MovieDetailReviewsEntity(
        299534,
        1,
        1,
        mutableListOf(
            ResultMovieDetailReviewsItem(
                MovieDetailReviewsAuthorDetails(
                    null,
                    "",
                    null,
                    "WilliamJones"
                ),
                "2021-06-23T15:58:30.504Z",
                "WilliamJones",
                "2019-12-23T19:51:41.971Z",
                "5e011acd65686e001398cfc5",
                "That final fight with Thanos was epic! Great idea by the writers to have everybody be teleported back when Cap needed them the most. Officially the greatest superhero movie of all time!",
                "https://www.themoviedb.org/review/5e011acd65686e001398cfc5"
            )
        ),
        59
    )
    private val expectedException = Exception("Internal server error")
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        movieDetailReviewsViewModel = MovieDetailReviewsViewModel(getMovieDetailReviewsUseCase)
    }

    @Test
    fun `When get movie detail reviews return success`() = runTest {
        Mockito.`when`(getMovieDetailReviewsUseCase.invoke(tMovieIdAvengerEndGame, tPage))
            .thenReturn(
                flow {
                    emit(NetworkResponseState.Loading)
                    emit(NetworkResponseState.Success(tMovieDetailReviewsEntity))
                }
            )
        movieDetailReviewsViewModel.getMovieDetailReviews(tMovieIdAvengerEndGame, tPage)
        val state = movieDetailReviewsViewModel.ldMovieDetailReviewsState.value
        Truth.assertThat((state as MovieDetailReviewsUIState.Success).result == expectedMovieDetailReviewsEntity)
    }

    @Test
    fun `When get movie detail reviews return error`() = runTest {
        Mockito.`when`(getMovieDetailReviewsUseCase.invoke(tMovieIdAvengerEndGame, tPage))
            .thenReturn(
                flow {
                    emit(NetworkResponseState.Loading)
                    emit(NetworkResponseState.Error(tException))
                }
            )
        movieDetailReviewsViewModel.getMovieDetailReviews(tMovieIdAvengerEndGame, tPage)
        val state = movieDetailReviewsViewModel.ldMovieDetailReviewsState.value
        Truth.assertThat((state as MovieDetailReviewsUIState.Error).toString() == expectedException.toString())
    }

}