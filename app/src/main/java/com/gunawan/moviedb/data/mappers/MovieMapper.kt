package com.gunawan.moviedb.data.mappers

import com.gunawan.moviedb.data.models.MovieModel
import com.gunawan.moviedb.domain.entities.MovieEntity
import com.gunawan.moviedb.domain.entities.ResultMovieItem
import javax.inject.Inject

class MovieMapper @Inject constructor() {

    fun mapToEntity(model: MovieModel): MovieEntity {
        return MovieEntity(
            model.page,
            model.totalPages,
            model.results?.map {
                ResultMovieItem(
                    it?.overview,
                    it?.originalLanguage,
                    it?.originalTitle,
                    it?.video,
                    it?.title,
                    it?.genreIds?.map { genreId ->
                        genreId
                    },
                    it?.posterPath,
                    it?.backdropPath,
                    it?.releaseDate,
                    it?.popularity,
                    it?.voteAverage,
                    it?.id,
                    it?.adult,
                    it?.voteCount
                )
            },
            model.totalResults
        )
    }

}