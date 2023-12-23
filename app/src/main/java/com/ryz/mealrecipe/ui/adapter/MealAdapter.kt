package com.ryz.mealrecipe.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ryz.mealrecipe.common.loadImageUrl
import com.ryz.mealrecipe.data.local.entity.MealEntity
import com.ryz.mealrecipe.databinding.RowItemMealBinding

class MealAdapter(private val clickListener: (String) -> Unit) :
    ListAdapter<MealEntity, MealAdapter.MealViewHolder>(DIFF_CALLBACK) {
    inner class MealViewHolder(private val binding: RowItemMealBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MealEntity) {
            with(binding) {
                tvMeal.text = data.strMeal
                imgMeal.loadImageUrl(data.strMealThumb)
                itemView.setOnClickListener {
                    clickListener(data.idMeal.toString())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val binding =
            RowItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
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