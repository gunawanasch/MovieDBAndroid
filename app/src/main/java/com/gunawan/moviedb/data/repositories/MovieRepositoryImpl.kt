package com.gunawan.moviedb.data.repositories

import com.gunawan.moviedb.core.network.NetworkResponseState
import com.gunawan.moviedb.data.datasources.MovieRemoteDataSource
import com.gunawan.moviedb.data.mappers.MovieDetailMapper
import com.gunawan.moviedb.data.mappers.MovieDetailReviewsMapper
import com.gunawan.moviedb.data.mappers.MovieGenresMapper
import com.gunawan.moviedb.data.mappers.MovieMapper
import com.gunawan.moviedb.data.models.MovieDetailModel
import com.gunawan.moviedb.data.models.MovieDetailReviewsModel
import com.gunawan.moviedb.data.models.MovieGenresModel
import com.gunawan.moviedb.data.models.MovieModel
import com.gunawan.moviedb.di.IoDispatcher
import com.gunawan.moviedb.domain.entities.MovieDetailEntity
import com.gunawan.moviedb.domain.entities.MovieDetailReviewsEntity
import com.gunawan.moviedb.domain.entities.MovieEntity
import com.gunawan.moviedb.domain.entities.MovieGenresEntity
import com.gunawan.moviedb.domain.repositories.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val movieGenresMapper: MovieGenresMapper,
    private val movieMapper: MovieMapper,
    private val movieDetailMapper: MovieDetailMapper,
    private val movieDetailReviewsMapper: MovieDetailReviewsMapper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : MovieRepository {

    override suspend fun getMovieGenres(): Flow<NetworkResponseState<MovieGenresEntity>> =
        flow {
            emit(NetworkResponseState.Loading)
            when(val response = remoteDataSource.getMovieGenres()) {
                is NetworkResponseState.Error -> emit(response)
                NetworkResponseState.Loading->Unit
                is NetworkResponseState.Success -> emit(
                    NetworkResponseState.Success(
                        movieGenresMapper.mapToEntity(
                            response.result ?: MovieGenresModel()
                        )
                    )
                )
            }
        }.flowOn(ioDispatcher)

    override suspend fun getMovie(genreId: Int, page: Int): Flow<NetworkResponseState<MovieEntity>> =
        flow {
            emit(NetworkResponseState.Loading)
            when(val response = remoteDataSource.getMovie(genreId, page)) {
                is NetworkResponseState.Error -> emit(response)
                NetworkResponseState.Loading->Unit
                is NetworkResponseState.Success -> emit(
                    NetworkResponseState.Success(
                        movieMapper.mapToEntity(
                            response.result ?: MovieModel()
                        )
                    )
                )
            }
        }.flowOn(ioDispatcher)

    override suspend fun getMovieDetail(movieId: Int): Flow<NetworkResponseState<MovieDetailEntity>> {
        return flow {
            emit(NetworkResponseState.Loading)
            when(val response = remoteDataSource.getMovieDetail(movieId)) {
                is NetworkResponseState.Error -> emit(response)
                NetworkResponseState.Loading->Unit
                is NetworkResponseState.Success -> emit(
                    NetworkResponseState.Success(
                        movieDetailMapper.mapToEntity(
                            response.result ?: MovieDetailModel()
                        )
                    )
                )
            }
        }.flowOn(ioDispatcher)
    }

    override suspend fun getMovieDetailReviews(
        movieId: Int,
        page: Int
    ): Flow<NetworkResponseState<MovieDetailReviewsEntity>> =
        flow {
            emit(NetworkResponseState.Loading)
            when(val response = remoteDataSource.getMovieDetailReviews(movieId, page)) {
                is NetworkResponseState.Error -> emit(response)
                NetworkResponseState.Loading->Unit
                is NetworkResponseState.Success -> emit(
                    NetworkResponseState.Success(
                        movieDetailReviewsMapper.mapToEntity(
                            response.result ?: MovieDetailReviewsModel()
                        )
                    )
                )
            }
        }.flowOn(ioDispatcher)

}