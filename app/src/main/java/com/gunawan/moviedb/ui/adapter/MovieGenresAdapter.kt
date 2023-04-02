package com.gunawan.moviedb.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.gunawan.moviedb.databinding.RowMovieGenresBinding
import com.gunawan.moviedb.repository.model.GenresItem

class MovieGenresAdapter(private val listGenres: List<GenresItem>) : RecyclerView.Adapter<MovieGenresAdapter.ViewHolder>() {
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
        return listGenres.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
        holder.binding.tvName.text = listGenres[position].name
        holder.binding.cvGenres.setOnClickListener {
            listener?.onItemClicked(listGenres[position])
        }
    }

    class ViewHolder(val binding: RowMovieGenresBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {}
    }

}