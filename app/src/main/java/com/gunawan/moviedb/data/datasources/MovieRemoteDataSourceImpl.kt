package com.gunawan.moviedb.data.datasources

import com.gunawan.moviedb.core.Constants.Companion.API_KEY
import com.gunawan.moviedb.core.network.NetworkResponseState
import com.gunawan.moviedb.data.models.MovieDetailModel
import com.gunawan.moviedb.data.models.MovieDetailReviewsModel
import com.gunawan.moviedb.data.models.MovieGenresModel
import com.gunawan.moviedb.data.models.MovieModel
import com.gunawan.moviedb.data.services.MovieService
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(private val apiService: MovieService) : MovieRemoteDataSource {

    override suspend fun getMovieGenres(): NetworkResponseState<MovieGenresModel> =
        try {
            val response =  apiService.getMovieGenres(API_KEY)
            NetworkResponseState.Success(response)
        } catch (e: Exception) {
            NetworkResponseState.Error(e)
        }

    override suspend fun getMovie(genreId: Int, page: Int): NetworkResponseState<MovieModel> =
        try {
            val response =  apiService.getMovie(API_KEY, genreId, page)
            NetworkResponseState.Success(response)
        } catch (e: Exception) {
            NetworkResponseState.Error(e)
        }

    override suspend fun getMovieDetail(movieId: Int): NetworkResponseState<MovieDetailModel> =
        try {
            val response =  apiService.getMovieDetail(movieId, API_KEY)
            NetworkResponseState.Success(response)
        } catch (e: Exception) {
            NetworkResponseState.Error(e)
        }

    override suspend fun getMovieDetailReviews(
        movieId: Int,
        page: Int
    ): NetworkResponseState<MovieDetailReviewsModel> =
        try {
            val response =  apiService.getMovieDetailReviews(movieId, API_KEY, page)
            NetworkResponseState.Success(response)
        } catch (e: Exception) {
            NetworkResponseState.Error(e)
        }

}