package com.gunawan.moviedb.data.datasources

import com.google.common.truth.Truth.assertThat
import com.gunawan.moviedb.core.Constants.Companion.API_KEY
import com.gunawan.moviedb.core.network.NetworkResponseState
import com.gunawan.moviedb.data.services.MovieService
import com.gunawan.moviedb.tGenreId
import com.gunawan.moviedb.tMovieDetailModel
import com.gunawan.moviedb.tMovieDetailReviewsModel
import com.gunawan.moviedb.tMovieGenresModel
import com.gunawan.moviedb.tMovieId
import com.gunawan.moviedb.tMovieIdAvengerEndGame
import com.gunawan.moviedb.tMovieModel
import com.gunawan.moviedb.tPage
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MovieRemoteDataSourceImplTest {
    @Mock
    private lateinit var movieService: MovieService
    private lateinit var movieRemoteDataSourceImpl: MovieRemoteDataSourceImpl

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        movieRemoteDataSourceImpl = MovieRemoteDataSourceImpl(movieService)
    }

    @Test
    fun `when get movie genres is state success`() {
        runBlocking {
            Mockito.`when`(movieService.getMovieGenres(API_KEY))
                .thenReturn(tMovieGenresModel)
            val response = movieRemoteDataSourceImpl.getMovieGenres()
            assertThat(response).isInstanceOf(NetworkResponseState.Success::class.java)
        }
    }

    @Test
    fun `when get movie is state success`() {
        runBlocking {
            Mockito.`when`(movieService.getMovie(API_KEY, tGenreId, tPage))
                .thenReturn(tMovieModel)
            val response = movieRemoteDataSourceImpl.getMovie(tGenreId, tPage)
            assertThat(response).isInstanceOf(NetworkResponseState.Success::class.java)
        }
    }

    @Test
    fun `when get movie detail is state success`() {
        runBlocking {
            Mockito.`when`(movieService.getMovieDetail(tMovieId, API_KEY))
                .thenReturn(tMovieDetailModel)
            val response = movieRemoteDataSourceImpl.getMovieDetail(tMovieId)
            assertThat(response).isInstanceOf(NetworkResponseState.Success::class.java)
        }
    }

    @Test
    fun `when get movie detail reviews is state success`() {
        runBlocking {
            Mockito.`when`(movieService.getMovieDetailReviews(tMovieIdAvengerEndGame, API_KEY, 1))
                .thenReturn(tMovieDetailReviewsModel)
            val response = movieRemoteDataSourceImpl.getMovieDetailReviews(tMovieIdAvengerEndGame, tPage)
            assertThat(response).isInstanceOf(NetworkResponseState.Success::class.java)
        }
    }

}