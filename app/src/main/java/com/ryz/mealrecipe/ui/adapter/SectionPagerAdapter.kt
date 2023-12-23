package com.ryz.mealrecipe.ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ryz.mealrecipe.ui.content.detail.DescriptionFragment
import com.ryz.mealrecipe.ui.content.detail.IngredientFragment
import com.ryz.mealrecipe.ui.content.detail.InstructionFragment

class SectionPagerAdapter(fragment: Fragment, private val data: Bundle) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = DescriptionFragment()
            1 -> fragment = InstructionFragment()
            2 -> fragment = IngredientFragment()
        }
        fragment?.arguments = data
        return fragment!!
    }
}