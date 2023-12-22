package com.ryz.mealrecipe.ui.content

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
class MainActivityViewModel @Inject constructor(private val repository: RecipeRepository) :
    ViewModel() {
    private val _listCategory = MutableLiveData<Resource<MealResponse>>()
    val listCategory: LiveData<Resource<MealResponse>> = _listCategory

    fun getListCategory() = viewModelScope.launch {
        repository.listByCategory().onStart {
            _listCategory.postValue(Resource.Loading())
        }.catch { exception ->
            _listCategory.postValue(Resource.Error(exception.message))
        }.collect { result ->
            _listCategory.postValue(Resource.Success(result))
        }
    }
}