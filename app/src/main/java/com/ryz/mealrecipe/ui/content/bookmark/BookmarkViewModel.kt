package com.ryz.mealrecipe.ui.content.bookmark

import androidx.lifecycle.ViewModel
import com.ryz.mealrecipe.data.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(private val repository: RecipeRepository) :
    ViewModel() {
}