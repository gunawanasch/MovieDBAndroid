package com.gunawan.moviedb.presentation.movie_detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.gunawan.moviedb.R
import com.gunawan.moviedb.databinding.ActivityMovieDetailBinding
import com.gunawan.moviedb.domain.entities.ResultMovieReviewsItem
import com.gunawan.moviedb.domain.entities.ResultMovieVideosItem
import com.gunawan.moviedb.core.Constants.Companion.BASE_IMAGE_URL
import com.gunawan.moviedb.core.Constants.Companion.BASE_YOUTUBE_URL
import com.gunawan.moviedb.presentation.movie_detail_reviews.MovieDetailReviewsActivity
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
    private val movieDetailViewModel: MovieDetailViewModel by viewModels()
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
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun getMovieDetail(movieId: Int) {
        movieDetailViewModel.getMovieDetail(movieId)
        movieDetailViewModel.ldMovieDetailState.observe(this) {
            when (it) {
                is MovieDetailUIState.Loading -> {
                    binding.appBar.visibility                           = View.GONE
                    binding.nsvDetail.visibility                        = View.GONE
                    binding.partialMovieDetail.tvLblVideo.visibility    = View.GONE
                    binding.partialMovieDetail.rvVideo.visibility       = View.GONE
                    binding.partialMovieDetail.tvLblReview.visibility   = View.GONE
                    binding.partialMovieDetail.tvLoadMore.visibility    = View.GONE
                    binding.partialMovieDetail.rvReview.visibility      = View.GONE
                    binding.pbLoading.visibility                        = View.VISIBLE
                }

                is MovieDetailUIState.Success -> {
                    binding.appBar.visibility       = View.VISIBLE
                    binding.nsvDetail.visibility    = View.VISIBLE
                    binding.pbLoading.visibility    = View.GONE

                    Glide.with(this).load(BASE_IMAGE_URL + it.result?.backdropPath).into(binding.ivBackdrop)
                    Glide.with(this).load(BASE_IMAGE_URL + it.result?.posterPath).into(binding.partialMovieDetail.ivPoster)

                    title = it.result?.title.toString()
                    binding.partialMovieDetail.tvTitle.text = title

                    val listGenre = it.result?.genres
                    if (!listGenre.isNullOrEmpty()) {
                        for (i in listGenre.indices) {
                            binding.partialMovieDetail.cgDetail.addChip(this, listGenre[i]?.name ?: "")
                        }
                    }

                    binding.partialMovieDetail.tvLblVote.text           = it.result?.voteCount.toString() + " " + getString(R.string.votes)
                    binding.partialMovieDetail.tvValueVote.text         = it.result?.voteAverage.toString()
                    binding.partialMovieDetail.tvValueLanguage.text     = it.result?.originalLanguage
                    binding.partialMovieDetail.tvValueOverview.text     = it.result?.overview

                    if (!it.result?.releaseDate.isNullOrBlank()) {
                        val date = SimpleDateFormat("yyyy-MM-dd").parse(it.result?.releaseDate.toString())
                        val pat = SimpleDateFormat().toLocalizedPattern().replace("\\W?[HhKkmsSzZXa]+\\W?".toRegex(), "")
                        val localFormatter = SimpleDateFormat(pat, Locale.getDefault())
                        binding.partialMovieDetail.tvValueReleaseDate.text = localFormatter.format(date!!)
                    }

                    val tempVideos = it.result?.videos?.results
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


                    val tempReviews = it.result?.reviews
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
                }

                is MovieDetailUIState.Error -> {
                    binding.appBar.visibility                           = View.GONE
                    binding.nsvDetail.visibility                        = View.GONE
                    binding.partialMovieDetail.tvLblVideo.visibility    = View.GONE
                    binding.partialMovieDetail.rvVideo.visibility       = View.GONE
                    binding.partialMovieDetail.tvLblReview.visibility   = View.GONE
                    binding.partialMovieDetail.tvLoadMore.visibility    = View.GONE
                    binding.partialMovieDetail.rvReview.visibility      = View.GONE
                    binding.pbLoading.visibility                        = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
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

}