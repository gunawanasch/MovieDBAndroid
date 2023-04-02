package com.gunawan.moviedb.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.gunawan.moviedb.R
import com.gunawan.moviedb.databinding.ActivityMainBinding
import com.gunawan.moviedb.repository.model.GenresItem
import com.gunawan.moviedb.ui.adapter.MovieGenresAdapter
import com.gunawan.moviedb.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var movieGenresAdapter: MovieGenresAdapter
    private val movieViewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tbOrder.title               = getString(R.string.app_name)
        binding.pbLoading.visibility        = View.VISIBLE
        binding.rvMovieGenres.visibility    = View.GONE

        getMovieGenres()
        getMovieGenresMsg()
    }

    private fun getMovieGenres() {
        movieViewModel.ldGetMovieGenres     = MutableLiveData()
        movieViewModel.ldMsg                = MutableLiveData()
        movieViewModel.getMovieGenres()
        movieViewModel.ldGetMovieGenres.observe(this) {
            if (!it.isNullOrEmpty()) {
                binding.pbLoading.visibility        = View.GONE
                binding.rvMovieGenres.visibility    = View.VISIBLE

                movieGenresAdapter = MovieGenresAdapter(it)
                binding.rvMovieGenres.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter = movieGenresAdapter
                    movieGenresAdapter.setOnCustomClickListener(object :
                        MovieGenresAdapter.OnCustomClickListener {
                        override fun onItemClicked(genresItem: GenresItem) {
                            intent = Intent(this@MainActivity, MovieActivity::class.java)
                            intent.putExtra("genreId", genresItem.id)
                            intent.putExtra("genreName", genresItem.name)
                            startActivity(intent)
                        }
                    })
                }
            } else {
                binding.pbLoading.visibility        = View.GONE
                binding.rvMovieGenres.visibility    = View.GONE
                Toast.makeText(this, getString(R.string.data_empty), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getMovieGenresMsg() {
        movieViewModel.ldMsg.observe(this) {
            binding.pbLoading.visibility        = View.GONE
            binding.rvMovieGenres.visibility    = View.GONE
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

}