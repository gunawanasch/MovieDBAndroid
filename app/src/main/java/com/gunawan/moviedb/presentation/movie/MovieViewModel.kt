package com.gunawan.moviedb.presentation.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gunawan.moviedb.core.network.NetworkResponseState
import com.gunawan.moviedb.domain.entities.MovieEntity
import com.gunawan.moviedb.domain.usecases.GetMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase
) : ViewModel() {
    private var mldMovieState = MutableLiveData<MovieUIState<MovieEntity>>()
    val ldMovieState: LiveData<MovieUIState<MovieEntity>> get() = mldMovieState

    fun getMovie(genreId: Int, page: Int) {
        viewModelScope.launch {
            getMovieUseCase.invoke(genreId, page).collectLatest { response ->
                when (response) {
                    is NetworkResponseState.Loading -> {
                        mldMovieState.postValue(MovieUIState.Loading)
                    }
                    is NetworkResponseState.Success -> {
                        mldMovieState.postValue(MovieUIState.Success(response.result))
                    }
                    is NetworkResponseState.Error -> {
                        mldMovieState.postValue(MovieUIState.Error(response.exception.toString()))
                    }
                }
            }
        }
    }

}