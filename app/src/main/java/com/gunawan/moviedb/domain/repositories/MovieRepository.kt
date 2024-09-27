package com.gunawan.moviedb.domain.repositories

import com.gunawan.moviedb.core.network.NetworkResponseState
import com.gunawan.moviedb.domain.entities.MovieDetailEntity
import com.gunawan.moviedb.domain.entities.MovieDetailReviewsEntity
import com.gunawan.moviedb.domain.entities.MovieEntity
import com.gunawan.moviedb.domain.entities.MovieGenresEntity
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovieGenres() : Flow<NetworkResponseState<MovieGenresEntity>>
    suspend fun getMovie(genreId: Int, page: Int) : Flow<NetworkResponseState<MovieEntity>>
    suspend fun getMovieDetail(movieId: Int) : Flow<NetworkResponseState<MovieDetailEntity>>
    suspend fun getMovieDetailReviews(movieId: Int, page: Int) : Flow<NetworkResponseState<MovieDetailReviewsEntity>>
}