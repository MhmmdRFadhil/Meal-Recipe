package com.ryz.mealrecipe.ui.content.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.ryz.mealrecipe.common.Resource
import com.ryz.mealrecipe.common.getBackStackData
import com.ryz.mealrecipe.data.local.entity.MealEntity
import com.ryz.mealrecipe.databinding.FragmentHomeBinding
import com.ryz.mealrecipe.ui.adapter.CategoryAdapter
import com.ryz.mealrecipe.ui.adapter.CategoryAdapterCallback
import com.ryz.mealrecipe.ui.adapter.MealAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), View.OnClickListener, CategoryAdapterCallback {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    private var type = 0

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var mealAdapter: MealAdapter
    private var mealName = "Beef"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCategoryList()
        getMealList()

        setupClickListener()
        setupTextWatcher()
        getFilter()
    }

    private fun setupClickListener() {
        binding.apply {
            btnBookmark.setOnClickListener(this@HomeFragment)
            btnFilter.setOnClickListener(this@HomeFragment)
        }
    }

    private fun setupTextWatcher() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                getSearchData(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun getFilter() {
        getBackStackData<Int>("filter_recipe") { result ->
            type = result
            getCategoryList()
        }
    }

    private fun getCategoryList() {
        when (type) {
            0 -> {
                homeViewModel.getCategoryList()
                homeViewModel.categoryList.observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            Log.d("listCategory", "${result.data?.meals}")
                            showCategoryRecyclerView(result.data?.meals, 0)
                        }

                        is Resource.Error -> Log.d("errorMsg", "${result.message}")
                    }
                }
            }

            1 -> {
                homeViewModel.getAreaList()
                homeViewModel.areaList.observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            Log.d("AreaCategory", "${result.data?.meals}")
                            showCategoryRecyclerView(result.data?.meals, 1)
                        }

                        is Resource.Error -> Log.d("errorMsg", "${result.message}")
                    }
                }
            }
        }
    }

    private fun getMealList() {
        homeViewModel.getMealByCategory(mealName)
        homeViewModel.filterCategory.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    Log.d("mealCategory", "${result.data?.meals}")
                    showMealRecyclerView(result.data?.meals)
                }

                is Resource.Error -> Log.d("errorMsg", "${result.message}")
            }
        }
    }

    private fun getSearchData(name: String) {
        homeViewModel.searchMeal(name)
        homeViewModel.searchMeal.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    Log.d("mealCategory", "${result.data?.meals}")
                    showMealRecyclerView(result.data?.meals)
                }

                is Resource.Error -> Log.d("errorMsg", "${result.message}")
            }
        }
    }

    private fun showCategoryRecyclerView(data: List<MealEntity?>?, type: Int) {
        binding.rvCategory.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            categoryAdapter = CategoryAdapter(type, this@HomeFragment)
            adapter = categoryAdapter
            categoryAdapter.submitList(data)
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

            if (data?.isNotEmpty() == true) {
                mealName = when(type) {
                    0 -> data[0]?.strCategory ?: ""
                    1 -> data[0]?.strArea ?: ""
                    else -> data[0]?.strCategory ?: ""
                }
            }
        }
    }

    private fun showMealRecyclerView(data: List<MealEntity>?) {
        binding.rvMeal.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 2)
            mealAdapter = MealAdapter(::onClickItem)
            adapter = mealAdapter
            mealAdapter.submitList(data)
        }
    }

    private fun onClickItem(id: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToHomeDetailFragment(id)
        findNavController().navigate(action)
    }

    override fun onClick(p0: View?) {
        binding.apply {
            when (p0?.id) {
                btnBookmark.id -> {
                    val action = HomeFragmentDirections.actionHomeFragmentToBookmarkFragment()
                    findNavController().navigate(action)
                }

                btnFilter.id -> {
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToFilterDialogFragment("filter_recipe")
                    action.type = type
                    findNavController().navigate(action)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(data: MealEntity, position: Int) {
        mealName = when (type) {
            0 -> data.strCategory.toString()
            1 -> data.strArea.toString()
            else -> data.strCategory.toString()
        }
        Log.d("mealName", "Category: ${data.strCategory}, Area: ${data.strArea}")
    }
}