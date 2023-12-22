package com.ryz.mealrecipe.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ryz.mealrecipe.data.local.dao.RecipeDao
import com.ryz.mealrecipe.data.local.entity.MealEntity

@Database(entities = [MealEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}