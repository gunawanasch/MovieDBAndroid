package com.gunawan.moviedb.domain.usecases

import com.google.common.truth.Truth
import com.gunawan.moviedb.core.network.NetworkResponseState
import com.gunawan.moviedb.domain.entities.MovieEntity
import com.gunawan.moviedb.domain.entities.ResultMovieItem
import com.gunawan.moviedb.domain.repositories.MovieRepository
import com.gunawan.moviedb.tException
import com.gunawan.moviedb.tGenreId
import com.gunawan.moviedb.tMovieEntity
import com.gunawan.moviedb.tPage
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
class GetMovieUseCaseMockitoTest {
    private lateinit var useCase: GetMovieUseCase
    @Mock
    private lateinit var movieRepository: MovieRepository
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

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = GetMovieUseCase(movieRepository)
    }

    @Test
    fun `When get movie return success`() = runTest {
        Mockito.`when`(movieRepository.getMovie(tGenreId, tPage))
            .thenReturn(
                flow {
                    emit(NetworkResponseState.Loading)
                    emit(NetworkResponseState.Success(tMovieEntity))
                }
            )
        val result = useCase.invoke(tGenreId, tPage)
        result.collectLatest {
            if (it is NetworkResponseState.Success) {
                Truth.assertThat(it.result).isEqualTo(expectedMovieEntity)
            }
        }
    }

    @Test
    fun `When get movie return error`() = runTest {
        Mockito.`when`(movieRepository.getMovie(tGenreId, tPage))
            .thenReturn(
                flow {
                    emit(NetworkResponseState.Loading)
                    emit(NetworkResponseState.Error(tException))
                }
            )
        val result = useCase.invoke(tGenreId, tPage)
        result.collectLatest {
            if (it is NetworkResponseState.Error) {
                Truth.assertThat(it.exception.toString()).isEqualTo(expectedException.toString())
            }
        }
    }

}