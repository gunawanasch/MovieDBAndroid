package com.gunawan.moviedb.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.gunawan.moviedb.R
import com.gunawan.moviedb.databinding.ActivityMovieDetailBinding
import com.gunawan.moviedb.repository.model.ResultMovieReviewsItem
import com.gunawan.moviedb.repository.model.ResultMovieVideosItem
import com.gunawan.moviedb.ui.adapter.MovieReviewsAdapter
import com.gunawan.moviedb.ui.adapter.MovieVideosAdapter
import com.gunawan.moviedb.utils.Constants.Companion.BASE_IMAGE_URL
import com.gunawan.moviedb.utils.Constants.Companion.BASE_YOUTUBE_URL
import com.gunawan.moviedb.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var movieVideosAdapter: MovieVideosAdapter
    private lateinit var movieReviewsAdapter: MovieReviewsAdapter
    private lateinit var listVideos: MutableList<ResultMovieVideosItem>
    private lateinit var listReviews: MutableList<ResultMovieReviewsItem>
    private val movieViewModel: MovieViewModel by viewModels()
    private var totalPageReviews: Int   = 1
    private var movieId: Int            = 0
    private var title: String           = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieId     = intent.getIntExtra("movieId", 0)

        binding.tbDetail.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
        binding.appBar.visibility                           = View.GONE
        binding.nsvDetail.visibility                        = View.GONE
        binding.partialMovieDetail.tvLblVideo.visibility    = View.GONE
        binding.partialMovieDetail.rvVideo.visibility       = View.GONE
        binding.partialMovieDetail.tvLblReview.visibility   = View.GONE
        binding.partialMovieDetail.tvLoadMore.visibility    = View.GONE
        binding.partialMovieDetail.rvReview.visibility      = View.GONE
        binding.pbLoading.visibility                        = View.VISIBLE


        listVideos  = mutableListOf()
        listReviews = mutableListOf()

        binding.tbDetail.setNavigationOnClickListener {
            finish()
        }


        handleCollapsedToolbarTitle()
        getMovieDetail(movieId)
        getMovieDetailMsg()
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun getMovieDetail(movieId: Int) {
        movieViewModel.ldGetMovieDetail   = MutableLiveData()
        movieViewModel.ldMsg              = MutableLiveData()
        movieViewModel.getMovieDetail(movieId)
        movieViewModel.ldGetMovieDetail.observe(this) {
            if (it != null) {
                binding.appBar.visibility       = View.VISIBLE
                binding.nsvDetail.visibility    = View.VISIBLE
                binding.pbLoading.visibility    = View.GONE

                Glide.with(this).load(BASE_IMAGE_URL + it.backdropPath).into(binding.ivBackdrop)
                Glide.with(this).load(BASE_IMAGE_URL + it.posterPath).into(binding.partialMovieDetail.ivPoster)

                title = it.title.toString()
                binding.partialMovieDetail.tvTitle.text = title

                if (!it.genres.isNullOrEmpty()) {
                    for (i in it.genres.indices) {
                        binding.partialMovieDetail.cgDetail.addChip(this, it.genres[i]?.name ?: "")
                    }
                }

                binding.partialMovieDetail.tvLblVote.text           = it.voteCount.toString() + " " + getString(R.string.votes)
                binding.partialMovieDetail.tvValueVote.text         = it.voteAverage.toString()
                binding.partialMovieDetail.tvValueLanguage.text     = it.originalLanguage
                binding.partialMovieDetail.tvValueOverview.text     = it.overview

                if (!it.releaseDate.isNullOrBlank()) {
                    val date = SimpleDateFormat("yyyy-MM-dd").parse(it.releaseDate)
                    val pat = SimpleDateFormat().toLocalizedPattern().replace("\\W?[HhKkmsSzZXa]+\\W?".toRegex(), "")
                    val localFormatter = SimpleDateFormat(pat, Locale.getDefault())
                    binding.partialMovieDetail.tvValueReleaseDate.text = localFormatter.format(date!!)
                }

                val tempVideos = it.videos?.results
                if (!tempVideos.isNullOrEmpty()) {
                    binding.partialMovieDetail.tvLblVideo.visibility  = View.VISIBLE
                    binding.partialMovieDetail.rvVideo.visibility     = View.VISIBLE

                    for (i in tempVideos.indices) {
                        listVideos.add(tempVideos[i]!!)
                    }

                    movieVideosAdapter = MovieVideosAdapter(listVideos)
                    binding.partialMovieDetail.rvVideo.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@MovieDetailActivity, LinearLayoutManager.HORIZONTAL, false)
                        adapter = movieVideosAdapter
                        movieVideosAdapter.setOnCustomClickListener(object :
                            MovieVideosAdapter.OnCustomClickListener {
                            override fun onItemClicked(resultMovieVideosItem: ResultMovieVideosItem) {
                                intent = Intent(Intent.ACTION_VIEW, Uri.parse(BASE_YOUTUBE_URL+resultMovieVideosItem.key))
                                startActivity(intent)
                            }
                        })
                    }
                } else {
                    binding.partialMovieDetail.tvLblVideo.visibility  = View.GONE
                    binding.partialMovieDetail.rvVideo.visibility     = View.GONE
                }


                val tempReviews = it.reviews
                if (tempReviews != null) {
                    val tempResultReviews = tempReviews.results
                    if (!tempResultReviews.isNullOrEmpty()) {
                        binding.partialMovieDetail.tvLblReview.visibility   = View.VISIBLE
                        binding.partialMovieDetail.rvReview.visibility      = View.VISIBLE
                        totalPageReviews                                    = tempReviews.totalPages ?: 0

                        if (totalPageReviews > 1) {
                            binding.partialMovieDetail.tvLoadMore.visibility    = View.VISIBLE
                        }

                        for (i in tempResultReviews.indices) {
                            listReviews.add(tempResultReviews[i]!!)
                        }

                        movieReviewsAdapter = MovieReviewsAdapter(listReviews.toMutableList())
                        binding.partialMovieDetail.rvReview.apply {
                            setHasFixedSize(true)
                            layoutManager = LinearLayoutManager(this@MovieDetailActivity)
                            adapter = movieReviewsAdapter
                            movieVideosAdapter.setOnCustomClickListener(object :
                                MovieVideosAdapter.OnCustomClickListener {
                                override fun onItemClicked(resultMovieVideosItem: ResultMovieVideosItem) {
                                    intent = Intent(Intent.ACTION_VIEW, Uri.parse(BASE_YOUTUBE_URL+resultMovieVideosItem.key))
                                    startActivity(intent)
                                }
                            })
                        }

                        binding.partialMovieDetail.tvLoadMore.setOnClickListener {
                            intent = Intent(this@MovieDetailActivity, MovieDetailReviewsActivity::class.java)
                            intent.putExtra("movieId", movieId)
                            startActivity(intent)
                        }
                    } else {
                        binding.partialMovieDetail.tvLblReview.visibility   = View.GONE
                        binding.partialMovieDetail.tvLoadMore.visibility    = View.GONE
                        binding.partialMovieDetail.rvReview.visibility      = View.GONE
                    }
                } else {
                    binding.partialMovieDetail.tvLblReview.visibility   = View.GONE
                    binding.partialMovieDetail.tvLoadMore.visibility    = View.GONE
                    binding.partialMovieDetail.rvReview.visibility      = View.GONE
                }

            } else {
                binding.appBar.visibility                           = View.GONE
                binding.nsvDetail.visibility                        = View.GONE
                binding.partialMovieDetail.tvLblVideo.visibility    = View.GONE
                binding.partialMovieDetail.rvVideo.visibility       = View.GONE
                binding.partialMovieDetail.tvLblReview.visibility   = View.GONE
                binding.partialMovieDetail.tvLoadMore.visibility    = View.GONE
                binding.partialMovieDetail.rvReview.visibility      = View.GONE
                binding.pbLoading.visibility                        = View.GONE
                Toast.makeText(this, getString(R.string.data_empty), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getMovieDetailMsg() {
        movieViewModel.ldMsg.observe(this) {
            binding.appBar.visibility                           = View.GONE
            binding.nsvDetail.visibility                        = View.GONE
            binding.partialMovieDetail.tvLblVideo.visibility    = View.GONE
            binding.partialMovieDetail.rvVideo.visibility       = View.GONE
            binding.partialMovieDetail.tvLblReview.visibility   = View.GONE
            binding.partialMovieDetail.tvLoadMore.visibility    = View.GONE
            binding.partialMovieDetail.rvReview.visibility      = View.GONE
            binding.pbLoading.visibility                        = View.GONE
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun ChipGroup.addChip(context: Context, label: String){
        Chip(context).apply {
            id                      = View.generateViewId()
            text                    = label
            isClickable             = true
            isCheckable             = true
            setChipSpacingHorizontalResource(R.dimen.dimen_4)
            isCheckedIconVisible    = false
            isFocusable             = true
            addView(this)
        }
    }

    private fun handleCollapsedToolbarTitle() {
        binding.appBar.addOnOffsetChangedListener(object : OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    binding.collapsingToolbar.title = title
                    isShow = true
                } else if (isShow) {
                    binding.collapsingToolbar.title = " "
                    isShow = false
                }
            }
        })
    }

//    private fun loadMoreData() {
//        pageReviews += 1
//        movieReviewsAdapter.addLoadingView()
//        movieViewModel.ldGetMovie   = MutableLiveData()
//        movieViewModel.ldMsg        = MutableLiveData()
//        movieViewModel.getMovie(genreId, page)
//        movieViewModel.ldGetMovie.observe(this) {
//            movieAdapter.removeLoadingView()
//            scrollListener.setLoaded()
//
//            if (it != null) {
//                if (!it.results.isNullOrEmpty()) {
//                    for (i in it.results.indices) {
//                        listMovie.add(it.results[i]!!)
//                    }
//                }
//            }
//
//            movieAdapter.updateData(listMovie.toMutableList())
//            binding.rvMovie.post {
//                movieAdapter.notifyDataSetChanged()
//            }
//        }
//    }

}