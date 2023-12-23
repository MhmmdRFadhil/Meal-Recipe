package com.ryz.mealrecipe.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ryz.mealrecipe.common.loadImageUrl
import com.ryz.mealrecipe.databinding.RowItemIngredientBinding

class IngredientAdapter : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    private val listDifferForIngredient = AsyncListDiffer(this, diffUtilForIngredient)
    private val listDifferForMeasure = AsyncListDiffer(this, diffUtilForMeasure)

    var ingredients: List<String>
        get() = listDifferForIngredient.currentList
        set(value) = listDifferForIngredient.submitList(value)

    var measures: List<String>
        get() = listDifferForMeasure.currentList
        set(value) = listDifferForMeasure.submitList(value)

    inner class IngredientViewHolder(private val binding: RowItemIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredient: String, measure: String) {
            with(binding) {
                imgIngredient.loadImageUrl("https://www.themealdb.com/images/ingredients/${ingredient}-Small.png")
                tvIngredient.text = ingredient
                tvMeasure.text = measure
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val binding =
            RowItemIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientViewHolder(binding)
    }

    override fun getItemCount(): Int = ingredients.size

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredients[position]
        val measure = measures[position]
        holder.bind(ingredient, measure)
    }

    companion object {
        private val diffUtilForIngredient = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem
        }

        private val diffUtilForMeasure = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem
        }
    }
}