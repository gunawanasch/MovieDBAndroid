package com.gunawan.moviedb.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gunawan.moviedb.databinding.BottomProgressBarBinding
import com.gunawan.moviedb.databinding.RowMovieBinding
import com.gunawan.moviedb.repository.model.ResultMovieItem
import com.gunawan.moviedb.utils.Constants.Companion.BASE_IMAGE_URL
import com.gunawan.moviedb.utils.Constants.Companion.VIEW_TYPE_ITEM
import com.gunawan.moviedb.utils.Constants.Companion.VIEW_TYPE_LOADING

class MovieAdapter(private var listMovie: MutableList<ResultMovieItem?>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listener: OnCustomClickListener? = null

    interface OnCustomClickListener {
        fun onItemClicked(resultMovieItem: ResultMovieItem)
    }

    fun setOnCustomClickListener(listener: OnCustomClickListener) {
        this.listener = listener
    }

    fun updateData(newList: MutableList<ResultMovieItem?>) {
        this.listMovie = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            ItemViewHolder(RowMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        } else {
            val view = BottomProgressBarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (listMovie[position] == null) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_ITEM
        }
    }

    fun addLoadingView() {
        listMovie.add(null)
        notifyItemInserted(listMovie.size - 1)
    }

    fun removeLoadingView() {
        if (listMovie.size != 0) {
            listMovie.removeAt(listMovie.size - 1)
            notifyItemRemoved(listMovie.size)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.bind()
            Glide.with(holder.itemView.context).load(BASE_IMAGE_URL + (listMovie[position]?.posterPath ?: "")).into(holder.binding.ivPoster)
            holder.binding.tvTitle.text     = listMovie[position]?.title ?: ""
            holder.binding.tvContent.text   = listMovie[position]?.overview ?: ""
            holder.binding.cvMovie.setOnClickListener {
                listMovie[position]?.let { it1 ->
                    listener?.onItemClicked(it1)
                }
            }
        }
    }

    class ItemViewHolder(val binding: RowMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {}
    }

    class LoadingViewHolder(val binding: BottomProgressBarBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {}
    }

}