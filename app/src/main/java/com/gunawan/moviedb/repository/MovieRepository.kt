package com.gunawan.moviedb.repository

import com.gunawan.moviedb.repository.model.RespMovie
import com.gunawan.moviedb.repository.model.RespMovieDetail
import com.gunawan.moviedb.repository.model.RespMovieDetailReviews
import com.gunawan.moviedb.repository.model.RespMovieGenres
import com.gunawan.moviedb.utils.Constants.Companion.API_KEY
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(private val api: APIService) {

    fun getMovieGenres(): Observable<RespMovieGenres> {
        return api.getMovieGenres(API_KEY)
    }

    fun getMovie(genreId: Int, page: Int): Observable<RespMovie> {
        return api.getMovie(API_KEY, genreId, page)
    }

    fun getMovieDetail(movieId: Int): Observable<RespMovieDetail> {
        return api.getMovieDetail(movieId, API_KEY)
    }

    fun getMovieDetailReviews(movieId: Int, page: Int): Observable<RespMovieDetailReviews> {
        return api.getMovieDetailReviews(movieId, API_KEY, page)
    }

}