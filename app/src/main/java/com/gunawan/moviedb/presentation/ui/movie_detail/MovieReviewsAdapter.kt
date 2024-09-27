package com.gunawan.moviedb.presentation.ui.movie_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gunawan.moviedb.databinding.BottomProgressBarBinding
import com.gunawan.moviedb.databinding.RowMovieReviewsBinding
import com.gunawan.moviedb.domain.entities.ResultMovieReviewsItem
import com.gunawan.moviedb.core.Constants

class MovieReviewsAdapter(private var listReviews: MutableList<ResultMovieReviewsItem?>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun updateData(newList: MutableList<ResultMovieReviewsItem?>) {
        this.listReviews = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Constants.VIEW_TYPE_ITEM) {
            ItemViewHolder(RowMovieReviewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        } else {
            val view = BottomProgressBarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return listReviews.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (listReviews[position] == null) {
            Constants.VIEW_TYPE_LOADING
        } else {
            Constants.VIEW_TYPE_ITEM
        }
    }

    fun addLoadingView() {
        listReviews.add(null)
        notifyItemInserted(listReviews.size - 1)
    }

    fun removeLoadingView() {
        if (listReviews.size != 0) {
            listReviews.removeAt(listReviews.size - 1)
            notifyItemRemoved(listReviews.size)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.bind()
            Glide.with(holder.itemView.context).load(Constants.BASE_IMAGE_URL + (listReviews[position]?.authorDetails?.avatarPath ?: "")).into(holder.binding.ivAuthor)
            holder.binding.tvAuthor.text    = listReviews[position]?.author ?: ""
            holder.binding.tvContent.text   = listReviews[position]?.content ?: ""
        }
    }

    class ItemViewHolder(val binding: RowMovieReviewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {}
    }

    class LoadingViewHolder(val binding: BottomProgressBarBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {}
    }

}