package com.gunawan.moviedb.data.mappers

import com.gunawan.moviedb.data.models.MovieGenresModel
import com.gunawan.moviedb.domain.entities.GenresItem
import com.gunawan.moviedb.domain.entities.MovieGenresEntity
import javax.inject.Inject

class MovieGenresMapper @Inject constructor() {

    fun mapToEntity(model: MovieGenresModel): MovieGenresEntity {
        return MovieGenresEntity(
            model.genres?.map {
                GenresItem(
                    it?.name,
                    it?.id
                )
            }
        )
    }

}