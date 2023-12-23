package com.ryz.mealrecipe.ui.content.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.flexbox.FlexboxLayoutManager
import com.ryz.mealrecipe.common.Resource
import com.ryz.mealrecipe.data.local.entity.MealEntity
import com.ryz.mealrecipe.databinding.FragmentDescriptionBinding
import com.ryz.mealrecipe.ui.adapter.TagsAdapter
import com.ryz.mealrecipe.ui.content.detail.HomeDetailFragment.Companion.EXTRA_DATA
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DescriptionFragment : Fragment() {

    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!

    private val homeDetailViewModel: HomeDetailViewModel by viewModels()

    private lateinit var tagsAdapter: TagsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDescriptionBinding.inflate(inflater, container, false)
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
                    setData(result.data?.meals)
                    showRecyclerView(result.data?.meals)
                }

                is Resource.Error -> {}
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setData(data: List<MealEntity?>?) {
        binding.apply {
            data?.forEach { item ->
                item?.let {
                    val video =
                        "<iframe width=\"100%\" height=\"100%\" src=\"${it.strYoutube}\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>"
                    webView.loadData(video, "text/html", "utf-8")
                    webView.settings.javaScriptEnabled = true
                    webView.webChromeClient = WebChromeClient()
                }
            }
        }
    }

    private fun showRecyclerView(data: List<MealEntity>?) {
        binding.rvTags.apply {
            setHasFixedSize(true)
            layoutManager = FlexboxLayoutManager(activity)
            tagsAdapter = TagsAdapter()
            adapter = tagsAdapter
            data?.map {
                if (it.strTags.isNullOrEmpty()) {
                    isVisible = false
                    binding.tvTitleTags.isVisible = false
                } else {
                    val tags = it.strTags.split(",").toTypedArray()
                    tagsAdapter.submitList(tags.toList())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}