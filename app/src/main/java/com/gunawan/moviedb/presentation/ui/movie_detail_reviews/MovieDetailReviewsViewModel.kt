package com.gunawan.moviedb.presentation.ui.movie_detail_reviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gunawan.moviedb.core.network.NetworkResponseState
import com.gunawan.moviedb.domain.entities.MovieDetailReviewsEntity
import com.gunawan.moviedb.domain.usecases.GetMovieDetailReviewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailReviewsViewModel @Inject constructor(
    private val getMovieDetailReviewsUseCase: GetMovieDetailReviewsUseCase
) : ViewModel() {
    private var mldMovieDetailReviewsState = MutableLiveData<MovieDetailReviewsUIState<MovieDetailReviewsEntity>>()
    val ldMovieDetailReviewsState: LiveData<MovieDetailReviewsUIState<MovieDetailReviewsEntity>> get() = mldMovieDetailReviewsState

    fun getMovieDetailReviews(movieId: Int, page: Int) {
        viewModelScope.launch {
            getMovieDetailReviewsUseCase.invoke(movieId, page).collectLatest { response ->
                when (response) {
                    is NetworkResponseState.Loading -> {
                        mldMovieDetailReviewsState.postValue(MovieDetailReviewsUIState.Loading)
                    }
                    is NetworkResponseState.Success -> {
                        mldMovieDetailReviewsState.postValue(MovieDetailReviewsUIState.Success(response.result))
                    }
                    is NetworkResponseState.Error -> {
                        mldMovieDetailReviewsState.postValue(MovieDetailReviewsUIState.Error(response.exception.toString()))
                    }
                }
            }
        }
    }

}