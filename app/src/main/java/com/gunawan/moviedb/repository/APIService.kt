package com.gunawan.moviedb.repository

import com.gunawan.moviedb.repository.model.RespMovie
import com.gunawan.moviedb.repository.model.RespMovieDetail
import com.gunawan.moviedb.repository.model.RespMovieDetailReviews
import com.gunawan.moviedb.repository.model.RespMovieGenres
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @GET("genre/movie/list")
    fun getMovieGenres(@Query("api_key") apiKey: String): Observable<RespMovieGenres>

    @GET("discover/movie")
    fun getMovie(@Query("api_key") apiKey: String,
                 @Query("with_genres") genreId: Int,
                 @Query("page") page: Int): Observable<RespMovie>

    @GET("movie/{movieId}?append_to_response=videos,credits,reviews")
    fun getMovieDetail(@Path("movieId") movieId: Int,
                       @Query("api_key") apiKey: String): Observable<RespMovieDetail>

    @GET("movie/{movieId}/reviews")
    fun getMovieDetailReviews(@Path("movieId") movieId: Int,
                              @Query("api_key") apiKey: String,
                              @Query("page") page: Int): Observable<RespMovieDetailReviews>
}