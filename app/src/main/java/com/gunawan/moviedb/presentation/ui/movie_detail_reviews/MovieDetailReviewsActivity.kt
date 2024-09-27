package com.gunawan.moviedb.presentation.ui.movie_detail_reviews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gunawan.moviedb.R
import com.gunawan.moviedb.databinding.ActivityMovieDetailReviewsBinding
import com.gunawan.moviedb.domain.entities.AuthorReviewDetails
import com.gunawan.moviedb.domain.entities.ResultMovieReviewsItem
import com.gunawan.moviedb.presentation.ui.movie_detail.MovieReviewsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailReviewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailReviewsBinding
    private lateinit var movieReviewsAdapter: MovieReviewsAdapter
    private lateinit var listReviews: MutableList<ResultMovieReviewsItem>
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private val movieDetailReviewsViewModel: MovieDetailReviewsViewModel by viewModels()
    private var currentPage: Int        = 1
    private var tempCurrentPage: Int    = 1
    private var totalPage: Int          = 1
    private var movieId: Int            = 0
    private var isLoadMore: Boolean     = false
    private var isFirstGetData: Boolean = true

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
    }

    private fun getMovieDetailReviews() {
        movieDetailReviewsViewModel.getMovieDetailReviews(movieId, currentPage)
        movieDetailReviewsViewModel.ldMovieDetailReviewsState.observe(this) {
            when (it) {
                is MovieDetailReviewsUIState.Loading -> {
                    if (isFirstGetData) {
                        binding.pbLoading.visibility    = View.VISIBLE
                        binding.rvReview.visibility     = View.GONE
                    }
                }

                is MovieDetailReviewsUIState.Success -> {
                    binding.pbLoading.visibility    = View.GONE
                    binding.rvReview.visibility     = View.VISIBLE

                    if (isFirstGetData) {
                        isFirstGetData = false
                        totalPage = it.result?.totalPages ?: 0
                        val tempListReviews = it.result?.results
                        if (!tempListReviews.isNullOrEmpty()) {
                            for (i in tempListReviews.indices) {
                                listReviews.add(
                                    ResultMovieReviewsItem(
                                        AuthorReviewDetails(
                                            tempListReviews[i]?.authorDetails?.avatarPath,
                                            tempListReviews[i]?.authorDetails?.name,
                                            tempListReviews[i]?.authorDetails?.rating,
                                            tempListReviews[i]?.authorDetails?.username
                                        ),
                                        tempListReviews[i]?.updatedAt,
                                        tempListReviews[i]?.author,
                                        tempListReviews[i]?.createdAt,
                                        tempListReviews[i]?.id,
                                        tempListReviews[i]?.content,
                                        tempListReviews[i]?.url
                                    )
                                )
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
                        movieReviewsAdapter.removeLoadingView()
                        isLoadMore = false
                        val tempListReviews = it.result?.results
                        if (!tempListReviews.isNullOrEmpty()) {
                            for (i in tempListReviews.indices) {
                                listReviews.add(
                                    ResultMovieReviewsItem(
                                        AuthorReviewDetails(
                                            tempListReviews[i]?.authorDetails?.avatarPath,
                                            tempListReviews[i]?.authorDetails?.name,
                                            tempListReviews[i]?.authorDetails?.rating,
                                            tempListReviews[i]?.authorDetails?.username
                                        ),
                                        tempListReviews[i]?.updatedAt,
                                        tempListReviews[i]?.author,
                                        tempListReviews[i]?.createdAt,
                                        tempListReviews[i]?.id,
                                        tempListReviews[i]?.content,
                                        tempListReviews[i]?.url
                                    )
                                )
                            }
                        }

                        movieReviewsAdapter.updateData(listReviews.toMutableList())
                        binding.rvReview.post {
                            movieReviewsAdapter.notifyDataSetChanged()
                        }
                    }
                }

                is MovieDetailReviewsUIState.Error -> {
                    if (isFirstGetData) {
                        binding.pbLoading.visibility    = View.GONE
                        binding.rvReview.visibility     = View.VISIBLE
                    } else {
                        currentPage = tempCurrentPage
                        movieReviewsAdapter.removeLoadingView()
                        isLoadMore = false
                    }
                    Log.e("error msg", it.message)
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadMoreData() {
        tempCurrentPage = currentPage
        currentPage += 1
        isLoadMore = true
        movieReviewsAdapter.addLoadingView()
        movieDetailReviewsViewModel.getMovieDetailReviews(movieId, currentPage)
    }

    private fun getLoadMoreMsg() {
//        movieViewModel.ldMsg.observe(this) {
//            currentPage = tempCurrentPage
//            movieReviewsAdapter.removeLoadingView()
//            isLoadMore = false
//        }
    }

}