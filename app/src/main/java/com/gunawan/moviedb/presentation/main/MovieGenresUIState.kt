package com.gunawan.moviedb.presentation.main

sealed class MovieGenresUIState<out T> {
    object Loading: MovieGenresUIState<Nothing>()
    data class Success<out T>(val result: T?): MovieGenresUIState<T>()
    data class Error(val message: String): MovieGenresUIState<Nothing>()
}