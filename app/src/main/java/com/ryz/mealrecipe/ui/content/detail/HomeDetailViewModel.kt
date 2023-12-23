package com.ryz.mealrecipe.ui.content.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryz.mealrecipe.common.ErrorHelper
import com.ryz.mealrecipe.common.Resource
import com.ryz.mealrecipe.data.local.entity.MealEntity
import com.ryz.mealrecipe.data.remote.response.MealResponse
import com.ryz.mealrecipe.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeDetailViewModel @Inject constructor(private val repository: RecipeRepository) :
    ViewModel() {

    private val _homeDetail = MutableLiveData<Resource<MealResponse>>()
    val homeDetail: LiveData<Resource<MealResponse>> = _homeDetail

    fun getHomeDetail(id: String) = viewModelScope.launch {
        repository.lookUpWithId(id).onStart {
            _homeDetail.postValue(Resource.Loading())
        }.catch { exception ->
            val errorMessage = ErrorHelper.getErrorMessage(exception)
            _homeDetail.postValue(Resource.Error(errorMessage))
        }.collect { result ->
            _homeDetail.postValue(Resource.Success(result))
        }
    }

    fun insertRecipe(data: MealEntity) = viewModelScope.launch {
        repository.insertRecipe(data)
    }

    fun deleteRecipe(id: String) = viewModelScope.launch {
        repository.deleteRecipe(id)
    }

    fun isMealExists(id: String): Int = repository.isRowExists(id)
}