package com.gunawan.moviedb.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gunawan.moviedb.databinding.RowMovieGenresBinding
import com.gunawan.moviedb.domain.entities.GenresItem

class MovieGenresAdapter(private val listGenres: List<GenresItem?>?) : RecyclerView.Adapter<MovieGenresAdapter.ViewHolder>() {
    private var listener: OnCustomClickListener? = null

    interface OnCustomClickListener {
        fun onItemClicked(genresItem: GenresItem)
    }

    fun setOnCustomClickListener(listener: OnCustomClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RowMovieGenresBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return listGenres?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
        holder.binding.tvName.text = listGenres?.get(position)?.name
        holder.binding.cvGenres.setOnClickListener {
            listener?.onItemClicked(listGenres?.get(position) ?: GenresItem())
        }
    }

    class ViewHolder(val binding: RowMovieGenresBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {}
    }

}