package com.gunawan.moviedb.data.models

import com.google.gson.annotations.SerializedName

data class MovieGenresModel(

	@field:SerializedName("genres")
	val genres: List<GenresItem?>? = null
)

data class GenresItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)