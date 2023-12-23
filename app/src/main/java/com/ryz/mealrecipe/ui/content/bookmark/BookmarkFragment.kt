package com.ryz.mealrecipe.ui.content.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ryz.mealrecipe.MainActivity
import com.ryz.mealrecipe.R
import com.ryz.mealrecipe.common.customToolbar
import com.ryz.mealrecipe.data.local.entity.MealEntity
import com.ryz.mealrecipe.databinding.FragmentBookmarkBinding
import com.ryz.mealrecipe.ui.adapter.MealAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    private val bookmarkViewModel: BookmarkViewModel by viewModels()
    private lateinit var mealAdapter: MealAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        getMealListData()
    }

    private fun setupToolbar() {
        binding.layoutToolbars.topAppBar.customToolbar(
            (activity as MainActivity),
            title = getString(R.string.title_bookmark),
            isShowArrow = true
        )
    }

    private fun getMealListData() {
        bookmarkViewModel.getAllList().observe(viewLifecycleOwner) { result ->
            showRecyclerView(result)
        }
    }

    private fun showRecyclerView(data: List<MealEntity>?) {
        binding.rvBookmark.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 2)
            mealAdapter = MealAdapter(::onClickItem)
            adapter = mealAdapter
            mealAdapter.submitList(data)
        }
    }

    private fun onClickItem(id: String) {
        val action = BookmarkFragmentDirections.actionBookmarkFragmentToHomeDetailFragment(id)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}