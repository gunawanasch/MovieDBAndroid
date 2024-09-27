package com.gunawan.moviedb.data.mappers

import com.gunawan.moviedb.data.models.MovieDetailModel
import com.gunawan.moviedb.domain.entities.AuthorReviewDetails
import com.gunawan.moviedb.domain.entities.BelongsToCollection
import com.gunawan.moviedb.domain.entities.MovieDetailEntity
import com.gunawan.moviedb.domain.entities.MovieDetailGenresItem
import com.gunawan.moviedb.domain.entities.ProductionCompaniesItem
import com.gunawan.moviedb.domain.entities.ProductionCountriesItem
import com.gunawan.moviedb.domain.entities.ResultMovieReviewsItem
import com.gunawan.moviedb.domain.entities.ResultMovieVideosItem
import com.gunawan.moviedb.domain.entities.Reviews
import com.gunawan.moviedb.domain.entities.SpokenLanguagesItem
import com.gunawan.moviedb.domain.entities.Videos
import javax.inject.Inject

class MovieDetailMapper @Inject constructor() {

    fun mapToEntity(model: MovieDetailModel): MovieDetailEntity {
        return MovieDetailEntity(
            model.originalLanguage,
            model.imdbId,
            Videos(
               model.videos?.results?.map {
                   ResultMovieVideosItem(
                       it?.site,
                       it?.size,
                       it?.iso31661,
                       it?.name,
                       it?.official,
                       it?.id,
                       it?.type,
                       it?.publishedAt,
                       it?.iso6391,
                       it?.key
                   )
               }
            ),
            model.video,
            model.title,
            model.backdropPath,
            model.revenue,
            Reviews(
                model.reviews?.page,
                model.reviews?.totalPages,
                model.reviews?.results?.map {
                    ResultMovieReviewsItem(
                        AuthorReviewDetails(
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
                model.reviews?.totalResults
            ),
            model.genres?.map {
                MovieDetailGenresItem(
                    it?.name,
                    it?.id
                )
            },
            model.popularity,
            model.productionCountries?.map {
                ProductionCountriesItem(
                    it?.iso31661,
                    it?.name
                )
            },
            model.id,
            model.voteCount,
            model.budget,
            model.overview,
            model.originalTitle,
            model.runtime,
            model.posterPath,
            model.spokenLanguages?.map {
                SpokenLanguagesItem(
                    it?.name,
                    it?.iso6391,
                    it?.englishName
                )
            },
            model.productionCompanies?.map {
                ProductionCompaniesItem(
                    it?.logoPath,
                    it?.name,
                    it?.id,
                    it?.originCountry
                )
            },
            model.releaseDate,
            model.voteAverage,
            BelongsToCollection(
                model.belongsToCollection?.backdropPath,
                model.belongsToCollection?.name,
                model.belongsToCollection?.id,
                model.belongsToCollection?.posterPath
            ),
            model.tagline,
            model.adult,
            model.homepage,
            model.status
        )
    }

}