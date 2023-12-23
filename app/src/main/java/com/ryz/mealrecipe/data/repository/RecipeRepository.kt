package com.ryz.mealrecipe.data.repository

import androidx.lifecycle.LiveData
import com.ryz.mealrecipe.data.local.entity.MealEntity
import com.ryz.mealrecipe.data.remote.response.MealResponse
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun insertRecipe(mealEntity: MealEntity)
    suspend fun deleteRecipe(id: String)
    fun getRecipe(): LiveData<List<MealEntity>>
    fun isRowExists(id: String?): Int
    suspend fun searchMealByName(searchWithName: String): Flow<MealResponse>
    suspend fun listByCategory(): Flow<MealResponse>
    suspend fun listByArea(): Flow<MealResponse>
    suspend fun filterByCategory(categoryName: String): Flow<MealResponse>
    suspend fun filterByArea(areaName: String): Flow<MealResponse>
    suspend fun lookUpWithId(mealId: String): Flow<MealResponse>
}