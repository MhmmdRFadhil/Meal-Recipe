package com.ryz.mealrecipe.di

import android.content.Context
import androidx.room.Room
import com.ryz.mealrecipe.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun injectRoomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "RecipeDB").build()

    @Singleton
    @Provides
    fun injectDao(database: AppDatabase) = database.recipeDao()
}