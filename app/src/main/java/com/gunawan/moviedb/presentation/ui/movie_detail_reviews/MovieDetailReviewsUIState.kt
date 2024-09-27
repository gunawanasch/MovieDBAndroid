package com.gunawan.moviedb.presentation.ui.movie_detail_reviews

sealed class MovieDetailReviewsUIState<out T : Any> {
    object Loading: MovieDetailReviewsUIState<Nothing>()
    data class Success<out T : Any>(val result: T?): MovieDetailReviewsUIState<T>()
    data class Error(val message: String): MovieDetailReviewsUIState<Nothing>()
}