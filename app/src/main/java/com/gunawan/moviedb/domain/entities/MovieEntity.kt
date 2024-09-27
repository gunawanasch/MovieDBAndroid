package com.gunawan.moviedb.domain.entities

import com.google.gson.annotations.SerializedName

data class MovieEntity(

    val page: Int? = null,

    val totalPages: Int? = null,

    val results: List<ResultMovieItem?>? = null,

    val totalResults: Int? = null

)

data class ResultMovieItem(

    val overview: String? = null,

    val originalLanguage: String? = null,

    val originalTitle: String? = null,

    val video: Boolean? = null,

    val title: String? = null,

    val genreIds: List<Int?>? = null,

    val posterPath: String? = null,

    val backdropPath: String? = null,

    val releaseDate: String? = null,

    val popularity: Double? = null,

    val voteAverage: Double? = null,

    val id: Int? = null,

    val adult: Boolean? = null,

    val voteCount: Int? = null

)