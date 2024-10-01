package com.gunawan.moviedb.data.repositories

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.gunawan.moviedb.core.network.NetworkResponseState
import com.gunawan.moviedb.data.mappers.MovieDetailMapper
import com.gunawan.moviedb.data.mappers.MovieDetailReviewsMapper
import com.gunawan.moviedb.data.mappers.MovieGenresMapper
import com.gunawan.moviedb.data.mappers.MovieMapper
import com.gunawan.moviedb.tGenreId
import com.gunawan.moviedb.tMovieId
import com.gunawan.moviedb.tMovieIdAvengerEndGame
import com.gunawan.moviedb.tPage
import com.gunawan.moviedb.tResponseEnum
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MovieRepositoryImplTest {
    @Mock
    private lateinit var movieGenresMapper: MovieGenresMapper
    @Mock
    private lateinit var movieMapper: MovieMapper
    @Mock
    private lateinit var movieDetailMapper: MovieDetailMapper
    @Mock
    private lateinit var movieDetailReviewsMapper: MovieDetailReviewsMapper
    private lateinit var fakeMovieRepository: FakeMovieRepository

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        fakeMovieRepository = FakeMovieRepository(movieGenresMapper, movieMapper, movieDetailMapper, movieDetailReviewsMapper)
    }

    @Test
    fun `when get movie genres is state loading`() =
        runBlocking {
            fakeMovieRepository.getMovieGenres().test {
                assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Loading::class.java)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when get movie genres is state loading and success sequentially`() =
        runBlocking {
            fakeMovieRepository.setRequest(tResponseEnum.SUCCESS)
            fakeMovieRepository.getMovieGenres().test {
                assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Loading::class.java)
                assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Success::class.java)
                awaitComplete()
            }
        }

    @Test
    fun `when get movie genres is state loading and failure sequentially`() =
        runBlocking {
            fakeMovieRepository.setRequest(tResponseEnum.ERROR)
            fakeMovieRepository.getMovieGenres().test {
                assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Loading::class.java)
                assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Error::class.java)
                awaitComplete()
            }
        }

    @Test
    fun `when get movie is state loading`() =
        runBlocking {
            fakeMovieRepository.getMovie(tGenreId, tPage).test {
                assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Loading::class.java)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when get movie is state loading and success sequentially`() =
        runBlocking {
            fakeMovieRepository.setRequest(tResponseEnum.SUCCESS)
            fakeMovieRepository.getMovie(tGenreId, tPage).test {
                assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Loading::class.java)
                assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Success::class.java)
                awaitComplete()
            }
        }

    @Test
    fun `when get movie is state loading and failure sequentially`() =
        runBlocking {
            fakeMovieRepository.setRequest(tResponseEnum.ERROR)
            fakeMovieRepository.getMovie(tGenreId, tPage).test {
                assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Loading::class.java)
                assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Error::class.java)
                awaitComplete()
            }
        }

    @Test
    fun `when get movie detail is state loading`() =
        runBlocking {
            fakeMovieRepository.getMovieDetail(tMovieId).test {
                assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Loading::class.java)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when get movie detail is state loading and success sequentially`() =
        runBlocking {
            fakeMovieRepository.setRequest(tResponseEnum.SUCCESS)
            fakeMovieRepository.getMovieDetail(tMovieId).test {
                assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Loading::class.java)
                assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Success::class.java)
                awaitComplete()
            }
        }

    @Test
    fun `when get movie detail is state loading and failure sequentially`() =
        runBlocking {
            fakeMovieRepository.setRequest(tResponseEnum.ERROR)
            fakeMovieRepository.getMovieDetail(tMovieId).test {
                assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Loading::class.java)
                assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Error::class.java)
                awaitComplete()
            }
        }

    @Test
    fun `when get movie detail reviews is state loading`() =
        runBlocking {
            fakeMovieRepository.getMovieDetailReviews(tMovieIdAvengerEndGame, tPage).test {
                assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Loading::class.java)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when get movie detail reviews is state loading and success sequentially`() =
        runBlocking {
            fakeMovieRepository.setRequest(tResponseEnum.SUCCESS)
            fakeMovieRepository.getMovieDetailReviews(tMovieIdAvengerEndGame, tPage).test {
                assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Loading::class.java)
                assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Success::class.java)
                awaitComplete()
            }
        }

    @Test
    fun `when get movie detail reviews is state loading and failure sequentially`() =
        runBlocking {
            fakeMovieRepository.setRequest(tResponseEnum.ERROR)
            fakeMovieRepository.getMovieDetailReviews(tMovieIdAvengerEndGame, tPage).test {
                assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Loading::class.java)
                assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Error::class.java)
                awaitComplete()
            }
        }

}