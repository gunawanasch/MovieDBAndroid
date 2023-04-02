package com.gunawan.moviedb.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gunawan.moviedb.databinding.RowMovieVideosBinding
import com.gunawan.moviedb.repository.model.GenresItem
import com.gunawan.moviedb.repository.model.ResultMovieVideosItem

class MovieVideosAdapter(private val listVideos: List<ResultMovieVideosItem>) : RecyclerView.Adapter<MovieVideosAdapter.ViewHolder>() {
    private var listener: OnCustomClickListener? = null

    interface OnCustomClickListener {
        fun onItemClicked(resultMovieVideosItem: ResultMovieVideosItem)
    }

    fun setOnCustomClickListener(listener: OnCustomClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RowMovieVideosBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return listVideos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
        Glide.with(holder.itemView.context).load(
            "https://img.youtube.com/vi/" + listVideos[position].key + "/hqdefault.jpg").into(holder.binding.ivTrailer)
        holder.binding.tvName.text = listVideos[position].name
        holder.binding.cvTrailer.setOnClickListener {
            listener?.onItemClicked(listVideos[position])
        }
    }

    class ViewHolder(val binding: RowMovieVideosBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {}
    }
}