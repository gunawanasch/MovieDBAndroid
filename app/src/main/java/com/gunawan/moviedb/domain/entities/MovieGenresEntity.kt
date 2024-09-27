package com.gunawan.moviedb.domain.entities

import com.google.gson.annotations.SerializedName

data class MovieGenresEntity(

    val genres: List<GenresItem?>? = null

)

data class GenresItem(

    val name: String? = null,

    val id: Int? = null

)