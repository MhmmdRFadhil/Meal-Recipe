package com.ryz.mealrecipe.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ryz.mealrecipe.R
import com.ryz.mealrecipe.data.local.entity.MealEntity
import com.ryz.mealrecipe.databinding.RowItemCategoryBinding

class CategoryAdapter(val type: Int, private val callback: CategoryAdapterCallback) :
    ListAdapter<MealEntity, CategoryAdapter.CategoryViewHolder>(DIFF_CALLBACK) {

    private var selectedItemPos = 0

    inner class CategoryViewHolder(private val binding: RowItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var mealEntity = MealEntity()

        init {
            itemView.setOnClickListener {
                selectedItemPos = adapterPosition
                notifyItemRangeChanged(0, currentList.size)
                callback.onClick(mealEntity, adapterPosition)
            }
        }

        fun bind(data: MealEntity) {
            with(binding) {
                mealEntity = data
                tvCategoryTitle.text = if (type == 0) data.strCategory else data.strArea
                if (adapterPosition == selectedItemPos) defaultState() else selectedState()
            }
        }

        private fun defaultState() {
            val semiBoldFont = ResourcesCompat.getFont(itemView.context, R.font.poppins_semi_bold)
            with(binding) {
                root.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.seed
                    )
                )
                tvCategoryTitle.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.white
                    )
                )
                tvCategoryTitle.typeface = semiBoldFont
            }
        }

        private fun selectedState() {
            val regularFont = ResourcesCompat.getFont(itemView.context, R.font.poppins_regular)
            with(binding) {
                root.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.md_theme_light_surfaceVariant
                    )
                )
                tvCategoryTitle.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.md_theme_light_primary
                    )
                )
                tvCategoryTitle.typeface = regularFont
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

interface CategoryAdapterCallback {
    fun onClick(data: MealEntity, position: Int)
}