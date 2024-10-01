package com.gunawan.moviedb.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.gunawan.moviedb.core.network.NetworkResponseState
import com.gunawan.moviedb.domain.entities.AuthorReviewDetails
import com.gunawan.moviedb.domain.entities.BelongsToCollection
import com.gunawan.moviedb.domain.entities.MovieDetailEntity
import com.gunawan.moviedb.domain.entities.MovieDetailGenresItem
import com.gunawan.moviedb.domain.entities.ProductionCompaniesItem
import com.gunawan.moviedb.domain.entities.ProductionCountriesItem
import com.gunawan.moviedb.domain.entities.ResultMovieReviewsItem
import com.gunawan.moviedb.domain.entities.ResultMovieVideosItem
import com.gunawan.moviedb.domain.entities.Reviews
import com.gunawan.moviedb.domain.entities.SpokenLanguagesItem
import com.gunawan.moviedb.domain.entities.Videos
import com.gunawan.moviedb.domain.usecases.GetMovieDetailUseCase
import com.gunawan.moviedb.presentation.movie_detail.MovieDetailUIState
import com.gunawan.moviedb.presentation.movie_detail.MovieDetailViewModel
import com.gunawan.moviedb.tException
import com.gunawan.moviedb.tMovieDetailEntity
import com.gunawan.moviedb.tMovieId
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
class MovieDetailViewModelTest {
    private lateinit var movieDetailViewModel: MovieDetailViewModel
    @Mock
    private lateinit var getMovieDetailUseCase: GetMovieDetailUseCase
    private val expectedMovieDetailEntity = MovieDetailEntity(
        "en",
        "tt6263850",
        Videos(
            mutableListOf(
                ResultMovieVideosItem(
                    "YouTube",
                    1080,
                    "US",
                    "On Digital Oct 1 & On Blu-ray Oct 22",
                    true,
                    "66f47517f5b4978643232294",
                    "Teaser",
                    "2024-09-25T14:00:18.000Z",
                    "en",
                    "LYaJVfiwv0w"
                )
            )
        ),
        false,
        "Deadpool & Wolverine",
        "/yDHYTfA3R0jFYba16jBB1ef8oIt.jpg",
        1321225740,
        Reviews(
            1,
            1,
            mutableListOf(
                ResultMovieReviewsItem(
                    AuthorReviewDetails(
                        null,
                        "",
                        6.0,
                        "shammahrashad"
                    ),
                    "2024-07-26T17:17:34.336Z",
                    "shammahrashad",
                    "2024-07-25T21:26:26.038Z",
                    "66a2c3023f13b7231f1b94eb",
                    "Theres not much of a plot and the villains weren't that great. It was a good laugh though and amazing cameos and fight scenes.",
                    "https://www.themoviedb.org/review/66a2c3023f13b7231f1b94eb"
                )
            )
        ),
        mutableListOf(
            MovieDetailGenresItem(
                "Action",
                28
            )
        ),
        2691.119,
        mutableListOf(
            ProductionCountriesItem(
                "US",
                "United States of America"
            )
        ),
        533535,
        3171,
        200000000,
        "A listless Wade Wilson toils away in civilian life with his days as the morally flexible mercenary, Deadpool, behind him. But when his homeworld faces an existential threat, Wade must reluctantly suit-up again with an even more reluctant Wolverine.",
        "Deadpool & Wolverine",
        128,
        "/8cdWjvZQUExUUTzyp4t6EDMubfO.jpg",
        mutableListOf(
            SpokenLanguagesItem(
                "English",
                "en",
                "English"
            )
        ),
        mutableListOf(
            ProductionCompaniesItem(
                "/hUzeosd33nzE5MCNsZxCGEKTXaQ.png",
                "Marvel Studios",
                420,
                "US"
            )
        ),
        "2024-07-24",
        7.7,
        BelongsToCollection(
            "/hBQOWY8qWXJVFAc8yLTh1teIu43.jpg",
            "Deadpool Collection",
            448150,
            "/30c5jO7YEXuF8KiWXLg9m28GWDA.jpg"
        ),
        "Come together.",
        false,
        "https://www.marvel.com/movies/deadpool-and-wolverine",
        "Released"
    )
    private val expectedException = Exception("Internal server error")
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        movieDetailViewModel = MovieDetailViewModel(getMovieDetailUseCase)
    }

    @Test
    fun `When get movie detail return success`() = runTest {
        Mockito.`when`(getMovieDetailUseCase.invoke(tMovieId))
            .thenReturn(
                flow {
                    emit(NetworkResponseState.Loading)
                    emit(NetworkResponseState.Success(tMovieDetailEntity))
                }
            )
        movieDetailViewModel.getMovieDetail(tMovieId)
        val state = movieDetailViewModel.ldMovieDetailState.value
        Truth.assertThat((state as MovieDetailUIState.Success).result == expectedMovieDetailEntity)
    }

    @Test
    fun `When get movie detail return error`() = runTest {
        Mockito.`when`(getMovieDetailUseCase.invoke(tMovieId))
            .thenReturn(
                flow {
                    emit(NetworkResponseState.Loading)
                    emit(NetworkResponseState.Error(tException))
                }
            )
        movieDetailViewModel.getMovieDetail(tMovieId)
        val state = movieDetailViewModel.ldMovieDetailState.value
        Truth.assertThat((state as MovieDetailUIState.Error).toString() == expectedException.toString())
    }

}