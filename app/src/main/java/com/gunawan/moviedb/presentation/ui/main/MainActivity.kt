package com.gunawan.moviedb.presentation.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gunawan.moviedb.R
import com.gunawan.moviedb.databinding.ActivityMainBinding
import com.gunawan.moviedb.domain.entities.GenresItem
import com.gunawan.moviedb.presentation.ui.movie.MovieActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var movieGenresAdapter: MovieGenresAdapter
    private val movieGenresViewModel: MovieGenresViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tbOrder.title               = getString(R.string.app_name)
        binding.pbLoading.visibility        = View.VISIBLE
        binding.rvMovieGenres.visibility    = View.GONE

        getMovieGenres()
    }

    private fun getMovieGenres() {
        movieGenresViewModel.getMovieGenres()
        movieGenresViewModel.ldMovieGenresState.observe(this) {
            when (it) {
                is MovieGenresUIState.Loading -> {
                    binding.pbLoading.visibility        = View.VISIBLE
                    binding.rvMovieGenres.visibility    = View.GONE
                }

                is MovieGenresUIState.Success -> {
                    binding.pbLoading.visibility        = View.GONE
                    binding.rvMovieGenres.visibility    = View.VISIBLE

                    movieGenresAdapter = MovieGenresAdapter(it.result?.genres)
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
                }

                is MovieGenresUIState.Error -> {
                    binding.pbLoading.visibility        = View.GONE
                    binding.rvMovieGenres.visibility    = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}