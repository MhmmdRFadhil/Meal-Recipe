package com.ryz.mealrecipe.ui.content.detail

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.ryz.mealrecipe.R
import com.ryz.mealrecipe.common.Resource
import com.ryz.mealrecipe.common.loadImageUrl
import com.ryz.mealrecipe.data.local.entity.MealEntity
import com.ryz.mealrecipe.databinding.FragmentHomeDetailBinding
import com.ryz.mealrecipe.ui.adapter.SectionPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeDetailFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentHomeDetailBinding? = null
    private val binding get() = _binding!!

    private val args: HomeDetailFragmentArgs by navArgs()
    private val homeDetailViewModel: HomeDetailViewModel by viewModels()
    private var mealEntity: MealEntity? = null
    private var isBookmark = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealEntity = MealEntity()

        setupClickListener()
        setupTabWithPager()
        getHomeDetail()

        isMealExists()
    }

    private fun setupClickListener() {
        binding.apply {
            btnBack.setOnClickListener(this@HomeDetailFragment)
            btnBookmark.setOnClickListener(this@HomeDetailFragment)
        }
    }

    private fun setupTabWithPager() {
        val bundle = Bundle().apply {
            putString(EXTRA_DATA, args.idMeal)
        }
        val adapter = SectionPagerAdapter(this, bundle)
        binding.apply {
            viewPager.adapter = adapter
            viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = getString(TAB_TITLE[position])
            }.attach()
        }
    }

    private fun getHomeDetail() {
        homeDetailViewModel.getHomeDetail(args.idMeal)
        homeDetailViewModel.homeDetail.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    Log.d("dataMeal", "${result.data?.meals}")
                    setHomeDetail(result.data?.meals)
                }

                is Resource.Error -> {}
            }
        }
    }

    private fun isMealExists() {
        CoroutineScope(Dispatchers.IO).launch {
            val count = homeDetailViewModel.isMealExists(args.idMeal)
            isBookmark = if (count > 0) {
                setBookmark(true)
                true
            } else {
                false
            }
        }
    }

    private fun setBookmark(isBookmark: Boolean) {
        binding.btnBookmark.backgroundTintList = if (isBookmark) ColorStateList.valueOf(
            ContextCompat.getColor(
                requireContext(),
                R.color.seed
            )
        ) else ColorStateList.valueOf(
            ContextCompat.getColor(
                requireContext(),
                R.color.md_theme_dark_onPrimaryContainer
            )
        )
    }

    private fun setHomeDetail(data: List<MealEntity?>?) {
        binding.apply {
            data?.forEach { item ->
                mealEntity = item
                item?.let {
                    imgMeal.loadImageUrl(it.strMealThumb)
                    tvMeal.text = it.strMeal
                }
            }
        }
    }

    override fun onClick(p0: View?) {
        binding.apply {
            when (p0?.id) {
                btnBack.id -> findNavController().navigate(HomeDetailFragmentDirections.actionHomeDetailFragmentToHomeFragment())
                btnBookmark.id -> {
                    isBookmark = !isBookmark
                    if (isBookmark) {
                        homeDetailViewModel.insertRecipe(mealEntity as MealEntity)
                    } else {
                        homeDetailViewModel.deleteRecipe(args.idMeal)
                    }
                    setBookmark(isBookmark)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @StringRes
        val TAB_TITLE = intArrayOf(
            R.string.title_description,
            R.string.title_instruction,
            R.string.title_ingredient
        )

        const val EXTRA_DATA = "extra_data"
    }
}