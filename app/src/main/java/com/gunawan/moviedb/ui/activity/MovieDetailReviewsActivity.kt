package com.gunawan.moviedb.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gunawan.moviedb.R
import com.gunawan.moviedb.databinding.ActivityMovieDetailReviewsBinding
import com.gunawan.moviedb.repository.model.AuthorReviewDetails
import com.gunawan.moviedb.repository.model.ResultMovieReviewsItem
import com.gunawan.moviedb.ui.adapter.MovieReviewsAdapter
import com.gunawan.moviedb.utils.RecyclerViewLoadMoreScroll
import com.gunawan.moviedb.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailReviewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailReviewsBinding
    private lateinit var movieReviewsAdapter: MovieReviewsAdapter
    private lateinit var listReviews: MutableList<ResultMovieReviewsItem>
    private lateinit var scrollListener: RecyclerViewLoadMoreScroll
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private val movieViewModel: MovieViewModel by viewModels()
    private var page: Int       = 1
    private var totalPage: Int  = 1
    private var movieId: Int    = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailReviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieId     = intent.getIntExtra("movieId", 0)

        binding.tbReview.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
        binding.tbReview.title              = getString(R.string.title_review)
        binding.pbLoading.visibility        = View.VISIBLE
        binding.rvReview.visibility         = View.GONE
        listReviews                         = mutableListOf()

        binding.tbReview.setNavigationOnClickListener {
            finish()
        }

        getMovieDetailReviews(movieId, page)
        getMovieDetailReviewsMsg()
    }

    private fun getMovieDetailReviews(movieId: Int, page: Int) {
        movieViewModel.ldGetMovieDetailReviews  = MutableLiveData()
        movieViewModel.ldMsg                    = MutableLiveData()
        movieViewModel.getMovieDetailReviews(movieId, page)
        movieViewModel.ldGetMovieDetailReviews.observe(this) {
            if (it != null) {
                binding.pbLoading.visibility        = View.GONE
                binding.rvReview.visibility         = View.VISIBLE

                totalPage = it.totalPages ?: 0
                if (!it.results.isNullOrEmpty()) {
                    for (i in it.results.indices) {
                        listReviews.add(ResultMovieReviewsItem(
                            AuthorReviewDetails(
                                it.results[i]?.authorDetails?.avatarPath,
                                it.results[i]?.authorDetails?.name,
                                it.results[i]?.authorDetails?.rating,
                                it.results[i]?.authorDetails?.username,
                            ),
                            it.results[i]?.updatedAt,
                            it.results[i]?.author,
                            it.results[i]?.createdAt,
                            it.results[i]?.id,
                            it.results[i]?.content,
                            it.results[i]?.url
                        ))
                    }

                    movieReviewsAdapter = MovieReviewsAdapter(listReviews.toMutableList())
                    mLayoutManager = LinearLayoutManager(this)
                    binding.rvReview.layoutManager = mLayoutManager
                    binding.rvReview.setHasFixedSize(true)
                    binding.rvReview.adapter = movieReviewsAdapter

                    mLayoutManager = LinearLayoutManager(this)
                    scrollListener = RecyclerViewLoadMoreScroll(mLayoutManager as LinearLayoutManager)
                    scrollListener.setOnLoadMoreListener(object :
                        RecyclerViewLoadMoreScroll.OnLoadMoreListener {
                        override fun onLoadMore() {
                            if (page < totalPage) {
                                loadMoreData()
                            }
                        }
                    })
                    binding.rvReview.addOnScrollListener(scrollListener)
                } else {
                    Toast.makeText(this, getString(R.string.data_empty), Toast.LENGTH_SHORT).show()
                }
            } else {
                binding.pbLoading.visibility    = View.GONE
                binding.rvReview.visibility      = View.GONE
                Toast.makeText(this, getString(R.string.data_empty), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getMovieDetailReviewsMsg() {
        movieViewModel.ldMsg.observe(this) {
            binding.pbLoading.visibility    = View.GONE
            binding.rvReview.visibility     = View.GONE
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadMoreData() {
        page += 1
        movieReviewsAdapter.addLoadingView()
        movieViewModel.ldGetMovieDetailReviews  = MutableLiveData()
        movieViewModel.ldMsg                    = MutableLiveData()
        movieViewModel.getMovieDetailReviews(movieId, page)
        movieViewModel.ldGetMovieDetailReviews.observe(this) {
            movieReviewsAdapter.removeLoadingView()
            scrollListener.setLoaded()

            if (it != null) {
                if (!it.results.isNullOrEmpty()) {
                    for (i in it.results.indices) {
                        listReviews.add(ResultMovieReviewsItem(
                            AuthorReviewDetails(
                                it.results[i]?.authorDetails?.avatarPath,
                                it.results[i]?.authorDetails?.name,
                                it.results[i]?.authorDetails?.rating,
                                it.results[i]?.authorDetails?.username,
                            ),
                            it.results[i]?.updatedAt,
                            it.results[i]?.author,
                            it.results[i]?.createdAt,
                            it.results[i]?.id,
                            it.results[i]?.content,
                            it.results[i]?.url
                        ))
                    }
                }
            }

            movieReviewsAdapter.updateData(listReviews.toMutableList())
            binding.rvReview.post {
                movieReviewsAdapter.notifyDataSetChanged()
            }
        }
    }

}