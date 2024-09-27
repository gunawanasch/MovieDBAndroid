package com.gunawan.moviedb.domain.entities

import com.google.gson.annotations.SerializedName

data class MovieDetailEntity(

    val originalLanguage: String? = null,

    val imdbId: String? = null,

    val videos: Videos? = null,

    val video: Boolean? = null,

    val title: String? = null,

    val backdropPath: String? = null,

    val revenue: Long? = null,

    val reviews: Reviews? = null,

    val genres: List<MovieDetailGenresItem?>? = null,

    val popularity: Double? = null,

    val productionCountries: List<ProductionCountriesItem?>? = null,

    val id: Int? = null,

    val voteCount: Long? = null,

    val budget: Int? = null,

    val overview: String? = null,

    val originalTitle: String? = null,

    val runtime: Int? = null,

    val posterPath: String? = null,

    val spokenLanguages: List<SpokenLanguagesItem?>? = null,

    val productionCompanies: List<ProductionCompaniesItem?>? = null,

    val releaseDate: String? = null,

    val voteAverage: Double? = null,

    val belongsToCollection: BelongsToCollection? = null,

    val tagline: String? = null,

    val adult: Boolean? = null,

    val homepage: String? = null,

    val status: String? = null

)

data class ProductionCompaniesItem(

    val logoPath: String? = null,

    val name: String? = null,

    val id: Int? = null,

    val originCountry: String? = null

)

data class Videos(

    val results: List<ResultMovieVideosItem?>? = null

)

data class ProductionCountriesItem(

    val iso31661: String? = null,

    val name: String? = null

)

data class SpokenLanguagesItem(

    val name: String? = null,

    val iso6391: String? = null,

    val englishName: String? = null

)

data class Reviews(

    val page: Int? = null,

    val totalPages: Int? = null,

    val results: List<ResultMovieReviewsItem?>? = null,

    val totalResults: Int? = null

)

data class ResultMovieVideosItem(

    val site: String? = null,

    val size: Int? = null,

    val iso31661: String? = null,

    val name: String? = null,

    val official: Boolean? = null,

    val id: String? = null,

    val type: String? = null,

    val publishedAt: String? = null,

    val iso6391: String? = null,

    val key: String? = null

)

data class ResultMovieReviewsItem(

    val authorDetails: AuthorReviewDetails? = null,

    val updatedAt: String? = null,

    val author: String? = null,

    val createdAt: String? = null,

    val id: String? = null,

    val content: String? = null,

    val url: String? = null

)

data class AuthorReviewDetails(

    val avatarPath: String? = null,

    val name: String? = null,

    val rating: Double? = null,

    val username: String? = null

)

data class BelongsToCollection(

    val backdropPath: String? = null,

    val name: String? = null,

    val id: Int? = null,

    val posterPath: String? = null

)

data class MovieDetailGenresItem(

    val name: String? = null,

    val id: Int? = null

)
