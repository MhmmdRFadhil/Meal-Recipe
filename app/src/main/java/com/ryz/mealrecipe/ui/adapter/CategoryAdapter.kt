package com.ryz.mealrecipe.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ryz.mealrecipe.data.local.entity.MealEntity
import com.ryz.mealrecipe.databinding.RowItemCategoryBinding

class CategoryAdapter(val type: Int) :
    ListAdapter<MealEntity, CategoryAdapter.CategoryViewHolder>(DIFF_CALLBACK) {
    inner class CategoryViewHolder(private val binding: RowItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MealEntity) {
            with(binding) {
                tvCategoryTitle.text = if (type == 0) data.strCategory else data.strArea
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            RowItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MealEntity>() {
            override fun areItemsTheSame(oldItem: MealEntity, newItem: MealEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MealEntity, newItem: MealEntity): Boolean =
                oldItem == newItem
        }
    }
}