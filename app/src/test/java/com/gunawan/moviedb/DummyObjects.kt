package com.gunawan.moviedb

import com.gunawan.moviedb.data.models.AuthorReviewDetails
import com.gunawan.moviedb.data.models.BelongsToCollection
import com.gunawan.moviedb.data.models.GenresItem
import com.gunawan.moviedb.data.models.MovieDetailGenresItem
import com.gunawan.moviedb.data.models.MovieDetailModel
import com.gunawan.moviedb.data.models.MovieDetailReviewsAuthorDetails
import com.gunawan.moviedb.data.models.MovieDetailReviewsModel
import com.gunawan.moviedb.data.models.MovieGenresModel
import com.gunawan.moviedb.data.models.MovieModel
import com.gunawan.moviedb.data.models.ProductionCompaniesItem
import com.gunawan.moviedb.data.models.ProductionCountriesItem
import com.gunawan.moviedb.data.models.ResultMovieDetailReviewsItem
import com.gunawan.moviedb.data.models.ResultMovieItem
import com.gunawan.moviedb.data.models.ResultMovieReviewsItem
import com.gunawan.moviedb.data.models.ResultMovieVideosItem
import com.gunawan.moviedb.data.models.Reviews
import com.gunawan.moviedb.data.models.SpokenLanguagesItem
import com.gunawan.moviedb.data.models.Videos
import com.gunawan.moviedb.domain.entities.MovieDetailEntity
import com.gunawan.moviedb.domain.entities.MovieDetailReviewsEntity
import com.gunawan.moviedb.domain.entities.MovieEntity
import com.gunawan.moviedb.domain.entities.MovieGenresEntity

val tMsgServerError = "Internal server error"
val tException = Exception(tMsgServerError)
val tMovieGenresModel = MovieGenresModel(
    mutableListOf(
        GenresItem(
            "Action",
            28
        )
    )
)
val tGenreId = 28
val tPage = 1
val tTotalPages = 46261

val tResultMovieItem = ResultMovieItem(
    "A listless Wade Wilson toils away in civilian life with his days as the morally flexible mercenary, Deadpool, behind him. But when his homeworld faces an existential threat, Wade must reluctantly suit-up again with an even more reluctant Wolverine.",
    "en",
    "Deadpool & Wolverine",
    false,
    "Deadpool & Wolverine",
    arrayListOf(28, 35, 878),
    "/8cdWjvZQUExUUTzyp4t6EDMubfO.jpg",
    "/yDHYTfA3R0jFYba16jBB1ef8oIt.jpg",
    "2024-07-24",
    2225.512,
    7.7,
    533535,
    false,
    3168
)

val tListMovie = mutableListOf(tResultMovieItem)
val tTotalResults = 925202
val tMovieModel = MovieModel(tPage, tTotalPages, tListMovie, tTotalResults)

val tMovieId = 533535

val tMovieDetailModel = MovieDetailModel(
    "en",
    "tt6263850",
    Videos(
        mutableListOf(
            ResultMovieVideosItem(
                "YouTube",
                1080,
                "US",
                "On Digital Oct 1 & On Blu-ray Oct 22",
                true,
                "66f47517f5b4978643232294",
                "Teaser",
                "2024-09-25T14:00:18.000Z",
                "en",
                "LYaJVfiwv0w"
            )
        )
    ),
    false,
    "Deadpool & Wolverine",
    "/yDHYTfA3R0jFYba16jBB1ef8oIt.jpg",
    1321225740,
    Reviews(
        1,
        1,
        mutableListOf(
            ResultMovieReviewsItem(
                AuthorReviewDetails(
                    null,
                    "",
                    6.0,
                    "shammahrashad"
                ),
                "2024-07-26T17:17:34.336Z",
                "shammahrashad",
                "2024-07-25T21:26:26.038Z",
                "66a2c3023f13b7231f1b94eb",
                "Theres not much of a plot and the villains weren't that great. It was a good laugh though and amazing cameos and fight scenes.",
                "https://www.themoviedb.org/review/66a2c3023f13b7231f1b94eb"
            )
        )
    ),
    mutableListOf(
        MovieDetailGenresItem(
            "Action",
            28
        )
    ),
    2691.119,
    mutableListOf(
        ProductionCountriesItem(
            "US",
            "United States of America"
        )
    ),
    533535,
    3171,
    200000000,
    "A listless Wade Wilson toils away in civilian life with his days as the morally flexible mercenary, Deadpool, behind him. But when his homeworld faces an existential threat, Wade must reluctantly suit-up again with an even more reluctant Wolverine.",
    "Deadpool & Wolverine",
    128,
    "/8cdWjvZQUExUUTzyp4t6EDMubfO.jpg",
    mutableListOf(
        SpokenLanguagesItem(
            "English",
            "en",
            "English"
        )
    ),
    mutableListOf(
        ProductionCompaniesItem(
            "/hUzeosd33nzE5MCNsZxCGEKTXaQ.png",
            "Marvel Studios",
            420,
            "US"
        )
    ),
    "2024-07-24",
    7.7,
    BelongsToCollection(
        "/hBQOWY8qWXJVFAc8yLTh1teIu43.jpg",
        "Deadpool Collection",
        448150,
        "/30c5jO7YEXuF8KiWXLg9m28GWDA.jpg"
    ),
    "Come together.",
    false,
    "https://www.marvel.com/movies/deadpool-and-wolverine",
    "Released"
)

val tMovieIdAvengerEndGame = 533535

val tMovieDetailReviewsModel = MovieDetailReviewsModel(
    299534,
    1,
    1,
    mutableListOf(
        ResultMovieDetailReviewsItem(
            MovieDetailReviewsAuthorDetails(
                null,
                "",
                null,
                "WilliamJones"
            ),
            "2021-06-23T15:58:30.504Z",
            "WilliamJones",
            "2019-12-23T19:51:41.971Z",
            "5e011acd65686e001398cfc5",
            "That final fight with Thanos was epic! Great idea by the writers to have everybody be teleported back when Cap needed them the most. Officially the greatest superhero movie of all time!",
            "https://www.themoviedb.org/review/5e011acd65686e001398cfc5"
        )
    ),
    59
)

enum class tResponseEnum{
    LOADING,
    SUCCESS,
    ERROR
}

val tMovieGenresEntity = MovieGenresEntity(
    mutableListOf(
        com.gunawan.moviedb.domain.entities.GenresItem(
            "Action",
            28
        )
    )
)

val tMovieEntity = MovieEntity(
    1,
    46261,
    mutableListOf(
        com.gunawan.moviedb.domain.entities.ResultMovieItem(
            "A listless Wade Wilson toils away in civilian life with his days as the morally flexible mercenary, Deadpool, behind him. But when his homeworld faces an existential threat, Wade must reluctantly suit-up again with an even more reluctant Wolverine.",
            "en",
            "Deadpool & Wolverine",
            false,
            "Deadpool & Wolverine",
            arrayListOf(28, 35, 878),
            "/8cdWjvZQUExUUTzyp4t6EDMubfO.jpg",
            "/yDHYTfA3R0jFYba16jBB1ef8oIt.jpg",
            "2024-07-24",
            2225.512,
            7.7,
            533535,
            false,
            3168
        )
    ),
    925202
)

val tMovieDetailEntity = MovieDetailEntity(
    "en",
    "tt6263850",
    com.gunawan.moviedb.domain.entities.Videos(
        mutableListOf(
            com.gunawan.moviedb.domain.entities.ResultMovieVideosItem(
                "YouTube",
                1080,
                "US",
                "On Digital Oct 1 & On Blu-ray Oct 22",
                true,
                "66f47517f5b4978643232294",
                "Teaser",
                "2024-09-25T14:00:18.000Z",
                "en",
                "LYaJVfiwv0w"
            )
        )
    ),
    false,
    "Deadpool & Wolverine",
    "/yDHYTfA3R0jFYba16jBB1ef8oIt.jpg",
    1321225740,
    com.gunawan.moviedb.domain.entities.Reviews(
        1,
        1,
        mutableListOf(
            com.gunawan.moviedb.domain.entities.ResultMovieReviewsItem(
                com.gunawan.moviedb.domain.entities.AuthorReviewDetails(
                    null,
                    "",
                    6.0,
                    "shammahrashad"
                ),
                "2024-07-26T17:17:34.336Z",
                "shammahrashad",
                "2024-07-25T21:26:26.038Z",
                "66a2c3023f13b7231f1b94eb",
                "Theres not much of a plot and the villains weren't that great. It was a good laugh though and amazing cameos and fight scenes.",
                "https://www.themoviedb.org/review/66a2c3023f13b7231f1b94eb"
            )
        )
    ),
    mutableListOf(
        com.gunawan.moviedb.domain.entities.MovieDetailGenresItem(
            "Action",
            28
        )
    ),
    2691.119,
    mutableListOf(
        com.gunawan.moviedb.domain.entities.ProductionCountriesItem(
            "US",
            "United States of America"
        )
    ),
    533535,
    3171,
    200000000,
    "A listless Wade Wilson toils away in civilian life with his days as the morally flexible mercenary, Deadpool, behind him. But when his homeworld faces an existential threat, Wade must reluctantly suit-up again with an even more reluctant Wolverine.",
    "Deadpool & Wolverine",
    128,
    "/8cdWjvZQUExUUTzyp4t6EDMubfO.jpg",
    mutableListOf(
        com.gunawan.moviedb.domain.entities.SpokenLanguagesItem(
            "English",
            "en",
            "English"
        )
    ),
    mutableListOf(
        com.gunawan.moviedb.domain.entities.ProductionCompaniesItem(
            "/hUzeosd33nzE5MCNsZxCGEKTXaQ.png",
            "Marvel Studios",
            420,
            "US"
        )
    ),
    "2024-07-24",
    7.7,
    com.gunawan.moviedb.domain.entities.BelongsToCollection(
        "/hBQOWY8qWXJVFAc8yLTh1teIu43.jpg",
        "Deadpool Collection",
        448150,
        "/30c5jO7YEXuF8KiWXLg9m28GWDA.jpg"
    ),
    "Come together.",
    false,
    "https://www.marvel.com/movies/deadpool-and-wolverine",
    "Released"
)

val tMovieDetailReviewsEntity = MovieDetailReviewsEntity(
    299534,
    1,
    1,
    mutableListOf(
        com.gunawan.moviedb.domain.entities.ResultMovieDetailReviewsItem(
            com.gunawan.moviedb.domain.entities.MovieDetailReviewsAuthorDetails(
                null,
                "",
                null,
                "WilliamJones"
            ),
            "2021-06-23T15:58:30.504Z",
            "WilliamJones",
            "2019-12-23T19:51:41.971Z",
            "5e011acd65686e001398cfc5",
            "That final fight with Thanos was epic! Great idea by the writers to have everybody be teleported back when Cap needed them the most. Officially the greatest superhero movie of all time!",
            "https://www.themoviedb.org/review/5e011acd65686e001398cfc5"
        )
    ),
    59
)