package com.gunawan.moviedb.domain.usecases

import com.gunawan.moviedb.core.network.NetworkResponseState
import com.gunawan.moviedb.domain.entities.MovieEntity
import com.gunawan.moviedb.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(private val repository: MovieRepository) {

    suspend fun invoke(genreId: Int, page: Int): Flow<NetworkResponseState<MovieEntity>> =
        repository.getMovie(genreId, page)

}