package com.ryz.mealrecipe.ui.content.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ryz.mealrecipe.common.setBackStackData
import com.ryz.mealrecipe.databinding.FragmentFilterDialogBinding

class FilterDialogFragment : BottomSheetDialogFragment(), View.OnClickListener {

    private var _binding: FragmentFilterDialogBinding? = null
    private val binding get() = _binding!!

    private val args: FilterDialogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListener()
        setFilter()
    }

    private fun setClickListener() {
        binding.apply {
            btnSave.setOnClickListener(this@FilterDialogFragment)
            btnCancel.setOnClickListener(this@FilterDialogFragment)
        }
    }

    private fun setFilter() {
        binding.apply {
            when (args.type) {
                0 -> rbFoods.isChecked = true
                1 -> rbAreas.isChecked = true
            }
        }
    }

    override fun onClick(p0: View?) {
        binding.apply {
            when (p0?.id) {
                btnSave.id -> {
                    val type = when {
                        rbFoods.isChecked -> 0
                        rbAreas.isChecked -> 1
                        else -> 0
                    }

                    setBackStackData(args.requestName, type, true)
                }

                btnCancel.id -> dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}