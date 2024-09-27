package com.gunawan.moviedb.data.datasources

import com.gunawan.moviedb.core.network.NetworkResponseState
import com.gunawan.moviedb.data.models.MovieDetailModel
import com.gunawan.moviedb.data.models.MovieDetailReviewsModel
import com.gunawan.moviedb.data.models.MovieGenresModel
import com.gunawan.moviedb.data.models.MovieModel

interface MovieRemoteDataSource {
    suspend fun getMovieGenres(): NetworkResponseState<MovieGenresModel>
    suspend fun getMovie(genreId: Int, page: Int): NetworkResponseState<MovieModel>
    suspend fun getMovieDetail(movieId: Int): NetworkResponseState<MovieDetailModel>
    suspend fun getMovieDetailReviews(movieId: Int, page: Int): NetworkResponseState<MovieDetailReviewsModel>
}