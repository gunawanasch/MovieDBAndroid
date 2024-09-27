package com.gunawan.moviedb.presentation.movie

sealed class MovieUIState<out T> {
    object Loading: MovieUIState<Nothing>()
    data class Success<out T>(val result: T?): MovieUIState<T>()
    data class Error(val message: String): MovieUIState<Nothing>()
}