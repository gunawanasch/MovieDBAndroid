package com.gunawan.moviedb.presentation.ui.main

sealed class MovieGenresUIState<out T : Any> {
    object Loading: MovieGenresUIState<Nothing>()
    data class Success<out T : Any>(val result: T?): MovieGenresUIState<T>()
    data class Error(val message: String): MovieGenresUIState<Nothing>()
}