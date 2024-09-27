package com.gunawan.moviedb.data.mappers

import com.gunawan.moviedb.data.models.MovieDetailReviewsModel
import com.gunawan.moviedb.domain.entities.MovieDetailReviewsAuthorDetails
import com.gunawan.moviedb.domain.entities.MovieDetailReviewsEntity
import com.gunawan.moviedb.domain.entities.ResultMovieDetailReviewsItem
import javax.inject.Inject

class MovieDetailReviewsMapper @Inject constructor() {

    fun mapToEntity(model: MovieDetailReviewsModel): MovieDetailReviewsEntity {
        return MovieDetailReviewsEntity(
            model.id,
            model.page,
            model.totalPages,
            model.results?.map {
                ResultMovieDetailReviewsItem(
                    MovieDetailReviewsAuthorDetails(
                        it?.authorDetails?.avatarPath,
                        it?.authorDetails?.name,
                        it?.authorDetails?.rating,
                        it?.authorDetails?.username
                    ),
                    it?.updatedAt,
                    it?.author,
                    it?.createdAt,
                    it?.id,
                    it?.content,
                    it?.url
                )
            },
            model.totalResults
        )
    }

}