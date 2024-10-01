package com.gunawan.moviedb.domain.usecases

import com.google.common.truth.Truth.assertThat
import com.gunawan.moviedb.core.network.NetworkResponseState
import com.gunawan.moviedb.domain.entities.GenresItem
import com.gunawan.moviedb.domain.entities.MovieGenresEntity
import com.gunawan.moviedb.domain.repositories.MovieRepository
import com.gunawan.moviedb.tException
import com.gunawan.moviedb.tMovieGenresEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class GetMovieGenresUseCaseMockitoTest {
    private lateinit var useCase: GetMovieGenresUseCase
    @Mock
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
        MockitoAnnotations.openMocks(this)
        useCase = GetMovieGenresUseCase(movieRepository)
    }

    @Test
    fun `When get movie genres return success`() = runTest {
        Mockito.`when`(movieRepository.getMovieGenres())
            .thenReturn(
                flow {
                    emit(NetworkResponseState.Loading)
                    emit(NetworkResponseState.Success(tMovieGenresEntity))
                }
            )
        val result = useCase.invoke()
        result.collectLatest {
            if (it is NetworkResponseState.Success) {
                assertThat(it.result).isEqualTo(expectedMovieGenresEntity)
            }
        }
    }

    @Test
    fun `When get movie genres return error`() = runTest {
        Mockito.`when`(movieRepository.getMovieGenres())
            .thenReturn(
                flow {
                    emit(NetworkResponseState.Loading)
                    emit(NetworkResponseState.Error(tException))
                }
            )
        val result = useCase.invoke()
        result.collectLatest {
            if (it is NetworkResponseState.Error) {
                assertThat(it.exception.toString()).isEqualTo(expectedException.toString())
            }
        }
    }

}