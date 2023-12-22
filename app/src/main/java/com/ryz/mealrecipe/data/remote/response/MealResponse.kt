package com.ryz.mealrecipe.data.remote.response

import com.ryz.mealrecipe.data.local.entity.MealEntity

data class MealResponse(
	val meals: List<MealEntity?>? = null
)
