package com.gunawan.moviedb.domain.usecases

import com.google.common.truth.Truth
import com.gunawan.moviedb.core.network.NetworkResponseState
import com.gunawan.moviedb.domain.entities.GenresItem
import com.gunawan.moviedb.domain.entities.MovieGenresEntity
import com.gunawan.moviedb.domain.repositories.MovieRepository
import com.gunawan.moviedb.tException
import com.gunawan.moviedb.tMovieGenresEntity
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
class GetMovieGenresUseCaseMockkTest {
    private lateinit var useCase: GetMovieGenresUseCase
    @MockK
    private lateinit var movieRepository: MovieRepository
    private val expectedMovieGenresEntity = MovieGenresEntity(
        mutableListOf(
            GenresItem(
                "Action",
                28
            )
        )
    )
    private val expectedException = Exception("Internal server error")

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetMovieGenresUseCase(movieRepository)
    }

    @Test
    fun `When get movie genres return success`() = runTest {
        coEvery { movieRepository.getMovieGenres() } returns flow {
            emit(NetworkResponseState.Loading)
            emit(NetworkResponseState.Success(tMovieGenresEntity))
        }
        val actualResult = useCase.invoke()
        actualResult.collectLatest {
            if (it is NetworkResponseState.Success) {
                Truth.assertThat(it.result).isEqualTo(expectedMovieGenresEntity)
            }
        }
    }

    @Test
    fun `When get movie genres return error`() = runTest {
        coEvery { movieRepository.getMovieGenres() } returns flow {
            emit(NetworkResponseState.Loading)
            emit(NetworkResponseState.Error(tException))
        }
        val actualResult = useCase.invoke()
        actualResult.collectLatest {
            if (it is NetworkResponseState.Error) {
                Truth.assertThat(it.exception.toString()).isEqualTo(expectedException.toString())
            }
        }
    }

}