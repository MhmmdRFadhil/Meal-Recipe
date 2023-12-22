package com.ryz.mealrecipe.data.repository

import androidx.lifecycle.LiveData
import com.ryz.mealrecipe.data.local.dao.RecipeDao
import com.ryz.mealrecipe.data.local.entity.MealEntity
import com.ryz.mealrecipe.data.remote.response.MealResponse
import com.ryz.mealrecipe.data.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepositoryImpl @Inject constructor(
    private val service: ApiService,
    private val dao: RecipeDao
) : RecipeRepository {
    override suspend fun insertRecipe(mealEntity: MealEntity) {
        dao.insertRecipe(mealEntity)
    }

    override suspend fun deleteRecipe(mealEntity: MealEntity) {
        dao.deleteRecipe(mealEntity)
    }

    override fun getRecipe(): LiveData<List<MealEntity>> = dao.getAllList()

    override suspend fun searchMealByName(searchWithName: String): Flow<MealResponse> = flow {
        val response = service.searchMealByName(searchWithName)
        emit(response)
    }.flowOn(Dispatchers.IO)

    override suspend fun listByCategory(): Flow<MealResponse> = flow {
        val response = service.listByCategory()
        emit(response)
    }.flowOn(Dispatchers.IO)

    override suspend fun listByArea(): Flow<MealResponse> = flow {
        val response = service.listByArea()
        emit(response)
    }.flowOn(Dispatchers.IO)

    override suspend fun filterByCategory(categoryName: String): Flow<MealResponse> = flow {
        val response = service.filterByCategory(categoryName)
        emit(response)
    }.flowOn(Dispatchers.IO)

    override suspend fun filterByArea(areaName: String): Flow<MealResponse> = flow {
        val response = service.filterByArea(areaName)
        emit(response)
    }.flowOn(Dispatchers.IO)

    override suspend fun lookUpWithId(mealId: Int): Flow<MealResponse> = flow {
        val response = service.lookUpWithId(mealId)
        emit(response)
    }.flowOn(Dispatchers.IO)
}