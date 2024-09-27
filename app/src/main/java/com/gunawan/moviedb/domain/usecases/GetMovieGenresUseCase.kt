package com.gunawan.moviedb.domain.usecases

import com.gunawan.moviedb.core.network.NetworkResponseState
import com.gunawan.moviedb.domain.entities.MovieGenresEntity
import com.gunawan.moviedb.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieGenresUseCase @Inject constructor(private val repository: MovieRepository) {

    suspend fun invoke(): Flow<NetworkResponseState<MovieGenresEntity>> =
        repository.getMovieGenres()

}