package com.ryz.mealrecipe.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ryz.mealrecipe.data.local.entity.MealEntity

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(mealEntity: MealEntity)

    @Query("DELETE FROM recipes WHERE idMeal =:idMeal")
    suspend fun deleteRecipe(idMeal: String)

    @Query("SELECT * FROM recipes")
    fun getAllList(): LiveData<List<MealEntity>>

    @Query("SELECT count(*) FROM recipes WHERE idMeal=:idMeal")
    fun isRowExists(idMeal: String?): Int
}