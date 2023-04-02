package com.gunawan.moviedb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gunawan.moviedb.repository.MovieRepository
import com.gunawan.moviedb.repository.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repo: MovieRepository) : ViewModel() {
    private var disposables         = CompositeDisposable()
    var ldGetMovieGenres            = MutableLiveData<List<GenresItem>>()
    var ldGetMovie                  = MutableLiveData<RespMovie>()
    var ldGetMovieDetail            = MutableLiveData<RespMovieDetail>()
    var ldGetMovieDetailReviews     = MutableLiveData<RespMovieDetailReviews>()
    var ldMsg                       = MutableLiveData<String>()

    fun getMovieGenres() {
        disposables.add(
            repo.getMovieGenres()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {}
                .doOnComplete {}
                .subscribe({ it ->
                    val listMovieGenres: MutableList<GenresItem> = mutableListOf()
                    if (it != null && !it.genres.isNullOrEmpty()) {
                        for (i in it.genres.indices) {
                            listMovieGenres.add(it.genres.get(i)!!)
                        }
                    }
                    ldGetMovieGenres.value = listMovieGenres
                }, {
                    ldMsg.value = it.message
                })
        )
    }

    fun getMovie(genreId: Int, page: Int) {
        disposables.add(
            repo.getMovie(genreId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {}
                .doOnComplete {}
                .subscribe({ it ->
                    ldGetMovie.value = it
                }, {
                    ldMsg.value = it.message
                })
        )
    }

    fun getMovieDetail(movieId: Int) {
        disposables.add(
            repo.getMovieDetail(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {}
                .doOnComplete {}
                .subscribe({ it ->
                    ldGetMovieDetail.value = it
                }, {
                    ldMsg.value = it.message
                })
        )
    }

    fun getMovieDetailReviews(movieId: Int, page: Int) {
        disposables.add(
            repo.getMovieDetailReviews(movieId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {}
                .doOnComplete {}
                .subscribe({ it ->
                    ldGetMovieDetailReviews.value = it
                }, {
                    ldMsg.value = it.message
                })
        )
    }

}