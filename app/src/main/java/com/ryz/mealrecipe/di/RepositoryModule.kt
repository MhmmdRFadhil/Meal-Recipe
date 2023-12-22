package com.ryz.mealrecipe.di

import com.ryz.mealrecipe.data.local.dao.RecipeDao
import com.ryz.mealrecipe.data.remote.retrofit.ApiService
import com.ryz.mealrecipe.data.repository.RecipeRepository
import com.ryz.mealrecipe.data.repository.RecipeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun injectRepository(service: ApiService, dao: RecipeDao): RecipeRepository =
        RecipeRepositoryImpl(service, dao)
}