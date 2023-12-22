package com.ryz.mealrecipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.ryz.mealrecipe.common.Resource
import com.ryz.mealrecipe.ui.content.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getListCategory()
    }

    private fun getListCategory() {
        mainActivityViewModel.getListCategory()

        mainActivityViewModel.listCategory.observe(this) { result ->
            when (result) {
                is Resource.Loading -> {}
                is Resource.Success -> Log.d("listCategory", "${result.data?.meals}")
                is Resource.Error -> Log.d("errorMsg", "${result.message}")
            }
        }
    }
}