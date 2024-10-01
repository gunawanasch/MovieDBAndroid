package com.gunawan.moviedb.data.repositories

import com.gunawan.moviedb.core.network.NetworkResponseState
import com.gunawan.moviedb.data.mappers.MovieDetailMapper
import com.gunawan.moviedb.data.mappers.MovieDetailReviewsMapper
import com.gunawan.moviedb.data.mappers.MovieGenresMapper
import com.gunawan.moviedb.data.mappers.MovieMapper
import com.gunawan.moviedb.domain.entities.MovieDetailEntity
import com.gunawan.moviedb.domain.entities.MovieDetailReviewsEntity
import com.gunawan.moviedb.domain.entities.MovieEntity
import com.gunawan.moviedb.domain.entities.MovieGenresEntity
import com.gunawan.moviedb.domain.repositories.MovieRepository
import com.gunawan.moviedb.tMovieDetailModel
import com.gunawan.moviedb.tMovieDetailReviewsModel
import com.gunawan.moviedb.tMovieGenresModel
import com.gunawan.moviedb.tMovieModel
import com.gunawan.moviedb.tResponseEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMovieRepository(
    private val movieGenresMapper: MovieGenresMapper,
    private val movieMapper: MovieMapper,
    private val movieDetailMapper: MovieDetailMapper,
    private val movieDetailReviewsMapper: MovieDetailReviewsMapper
) : MovieRepository {
    private var testRequest = tResponseEnum.LOADING

    fun setRequest(testRequest: tResponseEnum) {
        this.testRequest = testRequest
    }

    override suspend fun getMovieGenres(): Flow<NetworkResponseState<MovieGenresEntity>> =
        flow {
            emit(NetworkResponseState.Loading)
            when (testRequest) {
                tResponseEnum.LOADING -> emit(NetworkResponseState.Loading)
                tResponseEnum.ERROR -> emit(NetworkResponseState.Error(Exception("Error get data")))
                tResponseEnum.SUCCESS -> emit(NetworkResponseState.Success(movieGenresMapper.mapToEntity(
                    tMovieGenresModel
                )))
            }
        }

    override suspend fun getMovie(
        genreId: Int,
        page: Int
    ): Flow<NetworkResponseState<MovieEntity>> =
        flow {
            emit(NetworkResponseState.Loading)
            when (testRequest) {
                tResponseEnum.LOADING -> emit(NetworkResponseState.Loading)
                tResponseEnum.ERROR -> emit(NetworkResponseState.Error(Exception("Error get data")))
                tResponseEnum.SUCCESS -> emit(NetworkResponseState.Success(movieMapper.mapToEntity(
                    tMovieModel
                )))
            }
        }

    override suspend fun getMovieDetail(movieId: Int): Flow<NetworkResponseState<MovieDetailEntity>> =
        flow {
            emit(NetworkResponseState.Loading)
            when (testRequest) {
                tResponseEnum.LOADING -> emit(NetworkResponseState.Loading)
                tResponseEnum.ERROR -> emit(NetworkResponseState.Error(Exception("Error get data")))
                tResponseEnum.SUCCESS -> emit(NetworkResponseState.Success(movieDetailMapper.mapToEntity(
                    tMovieDetailModel
                )))
            }
        }

    override suspend fun getMovieDetailReviews(
        movieId: Int,
        page: Int
    ): Flow<NetworkResponseState<MovieDetailReviewsEntity>> =
        flow {
            emit(NetworkResponseState.Loading)
            when (testRequest) {
                tResponseEnum.LOADING -> emit(NetworkResponseState.Loading)
                tResponseEnum.ERROR -> emit(NetworkResponseState.Error(Exception("Error get data")))
                tResponseEnum.SUCCESS -> emit(NetworkResponseState.Success(movieDetailReviewsMapper.mapToEntity(
                    tMovieDetailReviewsModel
                )))
            }
        }

}