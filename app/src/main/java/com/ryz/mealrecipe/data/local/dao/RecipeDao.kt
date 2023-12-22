package com.ryz.mealrecipe.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ryz.mealrecipe.data.local.entity.MealEntity

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(mealEntity: MealEntity)

    @Delete
    suspend fun deleteRecipe(mealEntity: MealEntity)

    @Query("SELECT * FROM recipes")
    fun getAllList(): LiveData<List<MealEntity>>
}