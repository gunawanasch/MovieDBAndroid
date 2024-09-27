package com.gunawan.moviedb.presentation.movie_detail

sealed class MovieDetailUIState<out T> {
    object Loading: MovieDetailUIState<Nothing>()
    data class Success<out T>(val result: T?): MovieDetailUIState<T>()
    data class Error(val message: String): MovieDetailUIState<Nothing>()
}