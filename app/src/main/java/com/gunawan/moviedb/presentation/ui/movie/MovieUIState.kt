package com.gunawan.moviedb.presentation.ui.movie

sealed class MovieUIState<out T : Any> {
    object Loading: MovieUIState<Nothing>()
    data class Success<out T : Any>(val result: T?): MovieUIState<T>()
    data class Error(val message: String): MovieUIState<Nothing>()
}