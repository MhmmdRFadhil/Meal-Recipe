package com.ryz.mealrecipe.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ryz.mealrecipe.databinding.RowItemTagsBinding

class TagsAdapter : ListAdapter<String, TagsAdapter.TagsViewHolder>(DIFF_CALLBACK) {

    inner class TagsViewHolder(private val binding: RowItemTagsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tag: String) {
            with(binding) {
                tvTagsTitle.text = tag
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsViewHolder {
        val binding =
            RowItemTagsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TagsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TagsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem
        }
    }
}