package com.gunawan.moviedb.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gunawan.moviedb.R
import com.gunawan.moviedb.databinding.ActivityMovieBinding
import com.gunawan.moviedb.repository.model.ResultMovieItem
import com.gunawan.moviedb.ui.adapter.MovieAdapter
import com.gunawan.moviedb.utils.RecyclerViewLoadMoreScroll
import com.gunawan.moviedb.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieBinding
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var listMovie: MutableList<ResultMovieItem>
    private lateinit var scrollListener: RecyclerViewLoadMoreScroll
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private val movieViewModel: MovieViewModel by viewModels()
    private var page: Int           = 1
    private var totalPage: Int      = 1
    private var genreId: Int        = 0
    private var genreName: String   = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        genreId                             = intent.getIntExtra("genreId", 0)
        genreName                           = intent.getStringExtra("genreName").toString()

        binding.tbMovie.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
        binding.tbMovie.title               = getString(R.string.title_movie) + " " + genreName
        binding.pbLoading.visibility        = View.VISIBLE
        binding.rvMovie.visibility          = View.GONE
        listMovie                           = mutableListOf()

        binding.tbMovie.setNavigationOnClickListener {
            finish()
        }

        getMovie(genreId, page)
        getMovieMsg()
    }

    private fun getMovie(genreId: Int, page: Int) {
        movieViewModel.ldGetMovie   = MutableLiveData()
        movieViewModel.ldMsg        = MutableLiveData()
        movieViewModel.getMovie(genreId, page)
        movieViewModel.ldGetMovie.observe(this) {
            if (it != null) {
                binding.pbLoading.visibility        = View.GONE
                binding.rvMovie.visibility          = View.VISIBLE

                totalPage = it.totalPages ?: 0
                if (!it.results.isNullOrEmpty()) {
                    for (i in it.results.indices) {
                        listMovie.add(it.results[i]!!)
                    }
                    movieAdapter = MovieAdapter(listMovie.toMutableList())
                    mLayoutManager = LinearLayoutManager(this)
                    binding.rvMovie.layoutManager = mLayoutManager
                    binding.rvMovie.setHasFixedSize(true)
                    binding.rvMovie.adapter = movieAdapter
                    movieAdapter.setOnCustomClickListener(object : MovieAdapter.OnCustomClickListener {
                        override fun onItemClicked(resultMovieItem: ResultMovieItem) {
                            intent = Intent(this@MovieActivity, MovieDetailActivity::class.java)
                            intent.putExtra("movieId", resultMovieItem.id)
                            startActivity(intent)
                        }

                    })

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
                    binding.rvMovie.addOnScrollListener(scrollListener)
                } else {
                    Toast.makeText(this, getString(R.string.data_empty), Toast.LENGTH_SHORT).show()
                }
            } else {
                binding.pbLoading.visibility    = View.GONE
                binding.rvMovie.visibility      = View.GONE
                Toast.makeText(this, getString(R.string.data_empty), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getMovieMsg() {
        movieViewModel.ldMsg.observe(this) {
            binding.pbLoading.visibility    = View.GONE
            binding.rvMovie.visibility      = View.GONE
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadMoreData() {
        page += 1
        movieAdapter.addLoadingView()
        movieViewModel.ldGetMovie   = MutableLiveData()
        movieViewModel.ldMsg        = MutableLiveData()
        movieViewModel.getMovie(genreId, page)
        movieViewModel.ldGetMovie.observe(this) {
            movieAdapter.removeLoadingView()
            scrollListener.setLoaded()

            if (it != null) {
                if (!it.results.isNullOrEmpty()) {
                    for (i in it.results.indices) {
                        listMovie.add(it.results[i]!!)
                    }
                }
            }

            movieAdapter.updateData(listMovie.toMutableList())
            binding.rvMovie.post {
                movieAdapter.notifyDataSetChanged()
            }
        }
    }

}