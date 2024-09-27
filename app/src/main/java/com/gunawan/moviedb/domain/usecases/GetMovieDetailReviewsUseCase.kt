package com.gunawan.moviedb.domain.usecases

import com.gunawan.moviedb.core.network.NetworkResponseState
import com.gunawan.moviedb.domain.entities.MovieDetailReviewsEntity
import com.gunawan.moviedb.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailReviewsUseCase @Inject constructor(private val repository: MovieRepository) {

    suspend fun invoke(movieId: Int, page: Int): Flow<NetworkResponseState<MovieDetailReviewsEntity>> =
        repository.getMovieDetailReviews(movieId, page)

}