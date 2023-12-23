package com.ryz.mealrecipe.ui.content.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ryz.mealrecipe.common.Resource
import com.ryz.mealrecipe.common.addItemToList
import com.ryz.mealrecipe.data.local.entity.MealEntity
import com.ryz.mealrecipe.databinding.FragmentIngredientBinding
import com.ryz.mealrecipe.ui.adapter.IngredientAdapter
import com.ryz.mealrecipe.ui.content.detail.HomeDetailFragment.Companion.EXTRA_DATA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IngredientFragment : Fragment() {

    private var _binding: FragmentIngredientBinding? = null
    private val binding get() = _binding!!

    private val homeDetailViewModel: HomeDetailViewModel by viewModels()
    private lateinit var ingredientAdapter: IngredientAdapter

    private var ingredientList = mutableListOf<String>()
    private var measureList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIngredientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
    }

    private fun getData() {
        val id = arguments?.getString(EXTRA_DATA) ?: ""
        homeDetailViewModel.getHomeDetail(id)
        homeDetailViewModel.homeDetail.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    Log.d("mealDetail", "${result.data?.meals}")
                    showIngredientRecyclerView(result.data?.meals)
                }

                is Resource.Error -> {}
            }
        }
    }

    private fun getIngredientData(data: MealEntity) {
        addItemToList(data.strIngredient1, ingredientList)
        addItemToList(data.strIngredient2, ingredientList)
        addItemToList(data.strIngredient3, ingredientList)
        addItemToList(data.strIngredient4, ingredientList)
        addItemToList(data.strIngredient5, ingredientList)
        addItemToList(data.strIngredient6, ingredientList)
        addItemToList(data.strIngredient7, ingredientList)
        addItemToList(data.strIngredient8, ingredientList)
        addItemToList(data.strIngredient9, ingredientList)
        addItemToList(data.strIngredient10, ingredientList)
        addItemToList(data.strIngredient11, ingredientList)
        addItemToList(data.strIngredient12, ingredientList)
        addItemToList(data.strIngredient13, ingredientList)
        addItemToList(data.strIngredient14, ingredientList)
        addItemToList(data.strIngredient15, ingredientList)
        addItemToList(data.strIngredient16, ingredientList)
        addItemToList(data.strIngredient17, ingredientList)
        addItemToList(data.strIngredient18, ingredientList)
        addItemToList(data.strIngredient19, ingredientList)
        addItemToList(data.strIngredient20, ingredientList)

        addItemToList(data.strMeasure1, measureList)
        addItemToList(data.strMeasure2, measureList)
        addItemToList(data.strMeasure3, measureList)
        addItemToList(data.strMeasure4, measureList)
        addItemToList(data.strMeasure5, measureList)
        addItemToList(data.strMeasure6, measureList)
        addItemToList(data.strMeasure7, measureList)
        addItemToList(data.strMeasure8, measureList)
        addItemToList(data.strMeasure9, measureList)
        addItemToList(data.strMeasure10, measureList)
        addItemToList(data.strMeasure11, measureList)
        addItemToList(data.strMeasure12, measureList)
        addItemToList(data.strMeasure13, measureList)
        addItemToList(data.strMeasure14, measureList)
        addItemToList(data.strMeasure15, measureList)
        addItemToList(data.strMeasure16, measureList)
        addItemToList(data.strMeasure17, measureList)
        addItemToList(data.strMeasure18, measureList)
        addItemToList(data.strMeasure19, measureList)
        addItemToList(data.strMeasure20, measureList)
    }

    private fun showIngredientRecyclerView(data: List<MealEntity?>?) {
        binding.rvIngredient.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            ingredientAdapter = IngredientAdapter()
            adapter = ingredientAdapter

            data?.get(0)?.apply {
                getIngredientData(this)
            }

            ingredientAdapter.ingredients = ingredientList
            ingredientAdapter.measures = measureList
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}