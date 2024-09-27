package com.gunawan.moviedb.presentation.movie_detail_reviews

sealed class MovieDetailReviewsUIState<out T> {
    object Loading: MovieDetailReviewsUIState<Nothing>()
    data class Success<out T>(val result: T?): MovieDetailReviewsUIState<T>()
    data class Error(val message: String): MovieDetailReviewsUIState<Nothing>()
}