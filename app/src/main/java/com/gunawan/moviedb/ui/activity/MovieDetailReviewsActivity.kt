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
import com.gunawan.moviedb.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailReviewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailReviewsBinding
    private lateinit var movieReviewsAdapter: MovieReviewsAdapter
    private lateinit var listReviews: MutableList<ResultMovieReviewsItem>
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private val movieViewModel: MovieViewModel by viewModels()
    private var currentPage: Int        = 1
    private var tempCurrentPage: Int    = 1
    private var totalPage: Int          = 1
    private var movieId: Int            = 0
    private var isLoadMore: Boolean     = false

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

        getMovieDetailReviews()
        getMovieDetailReviewsMsg()
    }

    private fun getMovieDetailReviews() {
        movieViewModel.ldGetMovieDetailReviews  = MutableLiveData()
        movieViewModel.ldMsg                    = MutableLiveData()
        movieViewModel.getMovieDetailReviews(movieId, currentPage)
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
                    layoutManager = LinearLayoutManager(this)
                    binding.rvReview.layoutManager = layoutManager
                    binding.rvReview.setHasFixedSize(true)
                    binding.rvReview.adapter = movieReviewsAdapter

                    binding.rvReview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)
                            val visibleItemCount: Int           = layoutManager.childCount
                            val totalItemCount: Int             = layoutManager.itemCount
                            val firstVisibleItemPosition: Int   = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                            if (!isLoadMore) {
                                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                                    if (currentPage < totalPage) {
                                        loadMoreData()
                                        getLoadMoreMsg()
                                    }
                                }
                            }
                        }
                    })
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
        tempCurrentPage = currentPage
        currentPage += 1
        isLoadMore = true
        movieReviewsAdapter.addLoadingView()
        movieViewModel.ldGetMovieDetailReviews  = MutableLiveData()
        movieViewModel.ldMsg                    = MutableLiveData()
        movieViewModel.getMovieDetailReviews(movieId, currentPage)
        movieViewModel.ldGetMovieDetailReviews.observe(this) {
            movieReviewsAdapter.removeLoadingView()
            isLoadMore = false

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

    private fun getLoadMoreMsg() {
        movieViewModel.ldMsg.observe(this) {
            currentPage = tempCurrentPage
            movieReviewsAdapter.removeLoadingView()
            isLoadMore = false
        }
    }

}