package com.gunawan.moviedb.domain.usecases

import com.gunawan.moviedb.core.network.NetworkResponseState
import com.gunawan.moviedb.domain.entities.MovieDetailEntity
import com.gunawan.moviedb.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(private val repository: MovieRepository) {

    suspend fun invoke(movieId: Int): Flow<NetworkResponseState<MovieDetailEntity>> =
        repository.getMovieDetail(movieId)

}