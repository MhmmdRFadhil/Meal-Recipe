package com.ryz.mealrecipe.ui.content.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryz.mealrecipe.common.Resource
import com.ryz.mealrecipe.data.remote.response.MealResponse
import com.ryz.mealrecipe.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: RecipeRepository) :
    ViewModel() {
    private val _categoryList = MutableLiveData<Resource<MealResponse>>()
    val categoryList: LiveData<Resource<MealResponse>> = _categoryList

    private val _areaList = MutableLiveData<Resource<MealResponse>>()
    val areaList: LiveData<Resource<MealResponse>> = _areaList

    private val _filterCategory = MutableLiveData<Resource<MealResponse>>()
    val filterCategory: LiveData<Resource<MealResponse>> = _filterCategory

    private val _filterArea = MutableLiveData<Resource<MealResponse>>()
    val filterArea: LiveData<Resource<MealResponse>> = _filterArea

    fun getCategoryList() = viewModelScope.launch {
        repository.listByCategory().onStart {
            _categoryList.postValue(Resource.Loading())
        }.catch { exception ->
            _categoryList.postValue(Resource.Error(exception.message))
        }.collect { result ->
            _categoryList.postValue(Resource.Success(result))
        }
    }

    fun getAreaList() = viewModelScope.launch {
        repository.listByArea().onStart {
            _areaList.postValue(Resource.Loading())
        }.catch { exception ->
            _areaList.postValue(Resource.Error(exception.message))
        }.collect { result ->
            _areaList.postValue(Resource.Success(result))
        }
    }

    fun getMealByCategory(name: String) = viewModelScope.launch {
        repository.filterByCategory(name).onStart {
            _filterCategory.postValue(Resource.Loading())
        }.catch { exception ->
            _filterCategory.postValue(Resource.Error(exception.message))
        }.collect { result ->
            _filterCategory.postValue(Resource.Success(result))
        }
    }

    fun getMealByArea(area: String) = viewModelScope.launch {
        repository.filterByArea(area).onStart {
            _filterArea.postValue(Resource.Loading())
        }.catch { exception ->
            _filterArea.postValue(Resource.Error(exception.message))
        }.collect { result ->
            _filterArea.postValue(Resource.Success(result))
        }
    }
}