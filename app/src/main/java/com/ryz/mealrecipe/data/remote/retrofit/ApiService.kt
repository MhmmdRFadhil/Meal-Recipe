package com.ryz.mealrecipe.data.remote.retrofit

import com.ryz.mealrecipe.data.remote.response.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/json/v1/1/search.php")
    suspend fun searchMealByName(
        @Query("s") searchWithName: String
    ): MealResponse

    @GET("api/json/v1/1/list.php?c=list")
    suspend fun listByCategory(): MealResponse

    @GET("api/json/v1/1/list.php?a=list")
    suspend fun listByArea(): MealResponse

    @GET("api/json/v1/1/filter.php")
    suspend fun filterByCategory(
        @Query("c") categoryName: String
    ): MealResponse

    @GET("api/json/v1/1/filter.php")
    suspend fun filterByArea(
        @Query("a") areaName: String
    ): MealResponse

    @GET("api/json/v1/1/lookup.php")
    suspend fun lookUpWithId(
        @Query("i") mealId: String
    ): MealResponse
}