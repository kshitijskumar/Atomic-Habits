package com.example.atomichabits.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.atomichabits.data.response.PostResponse
import com.example.atomichabits.databinding.HolderPostBinding

class FeedAdapter : ListAdapter<PostResponse, FeedAdapter.FeedViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HolderPostBinding.inflate(inflater, parent, false)
        return FeedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindViews(item)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PostResponse>() {
            override fun areItemsTheSame(oldItem: PostResponse, newItem: PostResponse): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: PostResponse, newItem: PostResponse): Boolean {
                return oldItem.img == newItem.img
            }
        }
    }

    class FeedViewHolder(val binding: HolderPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindViews(item: PostResponse) {
            binding.apply {
                tvUser.text = item.name
                tvCaption.text = item.caption
            }
        }
    }
}