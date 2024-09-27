package com.gunawan.moviedb.domain.entities

import com.google.gson.annotations.SerializedName

data class MovieDetailReviewsEntity(

    val id: Int? = null,

    val page: Int? = null,

    val totalPages: Int? = null,

    val results: List<ResultMovieDetailReviewsItem?>? = null,

    val totalResults: Int? = null

)

data class MovieDetailReviewsAuthorDetails(

    val avatarPath: String? = null,

    val name: String? = null,

    val rating: Double? = null,

    val username: String? = null

)

data class ResultMovieDetailReviewsItem(

    val authorDetails: MovieDetailReviewsAuthorDetails? = null,

    val updatedAt: String? = null,

    val author: String? = null,

    val createdAt: String? = null,

    val id: String? = null,

    val content: String? = null,

    val url: String? = null

)