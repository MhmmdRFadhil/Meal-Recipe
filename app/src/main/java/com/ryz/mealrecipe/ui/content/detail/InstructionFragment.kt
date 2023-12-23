package com.ryz.mealrecipe.ui.content.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ryz.mealrecipe.common.Resource
import com.ryz.mealrecipe.data.local.entity.MealEntity
import com.ryz.mealrecipe.databinding.FragmentInstructionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InstructionFragment : Fragment() {

    private var _binding: FragmentInstructionBinding? = null
    private val binding get() = _binding!!

    private val homeDetailViewModel: HomeDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInstructionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
    }

    private fun getData() {
        val id = arguments?.getString(HomeDetailFragment.EXTRA_DATA) ?: ""

        homeDetailViewModel.getHomeDetail(id)
        homeDetailViewModel.homeDetail.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    Log.d("mealDetail", "${result.data?.meals}")
                    setData(result.data?.meals)
                }
                is Resource.Error -> {}
            }
        }
    }

    private fun setData(data: List<MealEntity?>?) {
        binding.apply {
            data?.forEach { item ->
                item?.let {
                    tvInfo.text = item.strInstructions
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}