package com.gunawan.moviedb.domain.usecases

import com.google.common.truth.Truth
import com.gunawan.moviedb.core.network.NetworkResponseState
import com.gunawan.moviedb.domain.entities.MovieDetailReviewsAuthorDetails
import com.gunawan.moviedb.domain.entities.MovieDetailReviewsEntity
import com.gunawan.moviedb.domain.entities.ResultMovieDetailReviewsItem
import com.gunawan.moviedb.domain.repositories.MovieRepository
import com.gunawan.moviedb.tException
import com.gunawan.moviedb.tMovieDetailReviewsEntity
import com.gunawan.moviedb.tMovieIdAvengerEndGame
import com.gunawan.moviedb.tPage
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetMovieDetailReviewsUseCaseMockkTest {
    private lateinit var useCase: GetMovieDetailReviewsUseCase
    @MockK
    private lateinit var movieRepository: MovieRepository
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

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetMovieDetailReviewsUseCase(movieRepository)
    }

    @Test
    fun `When get movie detail reviews return success`() = runTest {
        coEvery { movieRepository.getMovieDetailReviews(tMovieIdAvengerEndGame, tPage) } returns flow {
            emit(NetworkResponseState.Loading)
            emit(NetworkResponseState.Success(tMovieDetailReviewsEntity))
        }
        val actualResult = useCase.invoke(tMovieIdAvengerEndGame, tPage)
        actualResult.collectLatest {
            if (it is NetworkResponseState.Success) {
                Truth.assertThat(it.result).isEqualTo(expectedMovieDetailReviewsEntity)
            }
        }
    }

    @Test
    fun `When get movie detail reviews return error`() = runTest {
        coEvery { movieRepository.getMovieDetailReviews(tMovieIdAvengerEndGame, tPage) } returns flow {
            emit(NetworkResponseState.Loading)
            emit(NetworkResponseState.Error(tException))
        }
        val actualResult = useCase.invoke(tMovieIdAvengerEndGame, tPage)
        actualResult.collectLatest {
            if (it is NetworkResponseState.Error) {
                Truth.assertThat(it.exception.toString()).isEqualTo(expectedException.toString())
            }
        }
    }

}