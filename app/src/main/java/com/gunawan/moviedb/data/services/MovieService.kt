package com.gunawan.moviedb.data.services

import com.gunawan.moviedb.data.models.MovieDetailModel
import com.gunawan.moviedb.data.models.MovieDetailReviewsModel
import com.gunawan.moviedb.data.models.MovieGenresModel
import com.gunawan.moviedb.data.models.MovieModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("genress/movie/list")
    suspend fun getMovieGenres(@Query("api_key") apiKey: String): MovieGenresModel

    @GET("discover/movie")
    suspend fun getMovie(@Query("api_key") apiKey: String,
                 @Query("with_genres") genreId: Int,
                 @Query("page") page: Int): MovieModel

    @GET("movie/{movieId}?append_to_response=videos,credits,reviews")
    suspend fun getMovieDetail(@Path("movieId") movieId: Int,
                       @Query("api_key") apiKey: String): MovieDetailModel

    @GET("movie/{movieId}/reviews")
    suspend fun getMovieDetailReviews(@Path("movieId") movieId: Int,
                              @Query("api_key") apiKey: String,
                              @Query("page") page: Int): MovieDetailReviewsModel
}