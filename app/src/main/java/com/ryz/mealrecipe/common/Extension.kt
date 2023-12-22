package com.ryz.mealrecipe.common

import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide

fun <T : Any> Fragment.setBackStackData(key: String, data: T, doBack: Boolean = false) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, data)
    if (doBack) findNavController().navigateUp()
}

fun <T : Any> Fragment.getBackStackData(
    key: String,
    singleCall: Boolean = true,
    result: (T) -> (Unit)
) {
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)
        ?.observe(viewLifecycleOwner) {
            result(it)
            //if not removed then when click back without set data it will return previous data
            if (singleCall) findNavController().currentBackStackEntry?.savedStateHandle?.remove<T>(
                key
            )
        }
}

fun ImageView.loadImageUrl(image: String?) {
    Glide.with(this).load(image).into(this)
}