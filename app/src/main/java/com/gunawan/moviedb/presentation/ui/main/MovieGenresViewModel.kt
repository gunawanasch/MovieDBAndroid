package com.gunawan.moviedb.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gunawan.moviedb.core.network.NetworkResponseState
import com.gunawan.moviedb.domain.entities.MovieGenresEntity
import com.gunawan.moviedb.domain.usecases.GetMovieGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieGenresViewModel @Inject constructor(
    private val getMovieGenresUseCase: GetMovieGenresUseCase
) : ViewModel() {
    private var mldMovieGenresState = MutableLiveData<MovieGenresUIState<MovieGenresEntity>>()
    val ldMovieGenresState: LiveData<MovieGenresUIState<MovieGenresEntity>> get() = mldMovieGenresState

    fun getMovieGenres() {
        viewModelScope.launch {
            getMovieGenresUseCase.invoke().collectLatest { response ->
                when (response) {
                    is NetworkResponseState.Loading -> {
                        mldMovieGenresState.postValue(MovieGenresUIState.Loading)
                    }
                    is NetworkResponseState.Success -> {
                        mldMovieGenresState.postValue(MovieGenresUIState.Success(response.result))
                    }
                    is NetworkResponseState.Error -> {
                        mldMovieGenresState.postValue(MovieGenresUIState.Error(response.exception.toString()))
                    }
                }
            }
        }
    }

}