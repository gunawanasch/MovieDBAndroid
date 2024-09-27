package com.gunawan.moviedb.presentation.ui.movie_detail

sealed class MovieDetailUIState<out T : Any> {
    object Loading: MovieDetailUIState<Nothing>()
    data class Success<out T : Any>(val result: T?): MovieDetailUIState<T>()
    data class Error(val message: String): MovieDetailUIState<Nothing>()
}