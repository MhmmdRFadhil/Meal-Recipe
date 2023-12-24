package com.ryz.mealrecipe.ui.content.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.flexbox.FlexboxLayoutManager
import com.ryz.mealrecipe.common.Resource
import com.ryz.mealrecipe.common.extractVideoId
import com.ryz.mealrecipe.common.showMessage
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

        initializeWebView()
        getData()
    }

    private fun getData() {
        val id = arguments?.getString(EXTRA_DATA) ?: ""

        homeDetailViewModel.getHomeDetail(id)
        homeDetailViewModel.homeDetail.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> binding.loading.root.isVisible = true
                is Resource.Success -> {
                    binding.loading.root.isVisible = false
                    Log.d("mealDetail", "${result.data?.meals}")
                    setData(result.data?.meals)
                    showRecyclerView(result.data?.meals)
                }

                is Resource.Error -> {
                    binding.loading.root.isVisible = false
                    requireContext().showMessage(result.message)
                }
            }
        }
    }

    private fun setData(data: List<MealEntity?>?) {
        binding.apply {
            data?.forEach { item ->
                item?.let {
                    val embedUrl =
                        "https://www.youtube.com/embed/${extractVideoId(it.strYoutube.toString())}"
                    binding.webView.loadUrl(embedUrl)
                }
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initializeWebView() {
        binding.webView.apply {
            settings.javaScriptEnabled = true
            webChromeClient = WebChromeClient()
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view?.loadUrl(url.toString())
                    return true
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
                    isVisible = true
                    binding.tvTitleTags.isVisible = true
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