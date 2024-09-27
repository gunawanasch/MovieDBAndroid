package com.gunawan.moviedb.di

import com.gunawan.moviedb.domain.repositories.MovieRepository
import com.gunawan.moviedb.domain.usecases.GetMovieDetailReviewsUseCase
import com.gunawan.moviedb.domain.usecases.GetMovieDetailUseCase
import com.gunawan.moviedb.domain.usecases.GetMovieGenresUseCase
import com.gunawan.moviedb.domain.usecases.GetMovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetMovieGenresUseCase(repository: MovieRepository): GetMovieGenresUseCase {
        return GetMovieGenresUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetMovieUseCase(repository: MovieRepository): GetMovieUseCase {
        return GetMovieUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetMovieDetailUseCase(repository: MovieRepository): GetMovieDetailUseCase {
        return GetMovieDetailUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetMovieDetailReviewsUseCase(repository: MovieRepository): GetMovieDetailReviewsUseCase {
        return GetMovieDetailReviewsUseCase(repository)
    }

}