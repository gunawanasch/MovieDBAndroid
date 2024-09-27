package com.gunawan.moviedb.presentation.movie_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gunawan.moviedb.core.network.NetworkResponseState
import com.gunawan.moviedb.domain.entities.MovieDetailEntity
import com.gunawan.moviedb.domain.usecases.GetMovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : ViewModel() {
    private var mldMovieDetailState = MutableLiveData<MovieDetailUIState<MovieDetailEntity>>()
    val ldMovieDetailState: LiveData<MovieDetailUIState<MovieDetailEntity>> get() = mldMovieDetailState

    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            getMovieDetailUseCase.invoke(movieId).collectLatest { response ->
                when (response) {
                    is NetworkResponseState.Loading -> {
                        mldMovieDetailState.postValue(MovieDetailUIState.Loading)
                    }
                    is NetworkResponseState.Success -> {
                        mldMovieDetailState.postValue(MovieDetailUIState.Success(response.result))
                    }
                    is NetworkResponseState.Error -> {
                        mldMovieDetailState.postValue(MovieDetailUIState.Error(response.exception.toString()))
                    }
                }
            }
        }
    }

}