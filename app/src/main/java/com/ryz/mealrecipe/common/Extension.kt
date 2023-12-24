package com.ryz.mealrecipe.common

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.ryz.mealrecipe.R

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

fun addItemToList(item: String?, list: MutableList<String>) {
    if (item.isNullOrEmpty()) return
    else list.add(item)
}

fun MaterialToolbar.customToolbar(
    activity: AppCompatActivity,
    title: String? = null,
    isShowArrow: Boolean = false
) {
    with(this) {
        elevation = 4F
        setTitle(title)

        if (isShowArrow) {
            setNavigationIcon(R.drawable.baseline_arrow_back_24)
            setNavigationOnClickListener { activity.onBackPressedDispatcher.onBackPressed() }
        } else {
            navigationIcon = null
            setNavigationOnClickListener(null)
        }
    }
}

fun extractVideoId(videoUrl: String): String {
    val regex =
        "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*"
    val pattern = Regex(regex)
    val matchResult = pattern.find(videoUrl)
    return matchResult?.value.orEmpty()
}

fun Context.showMessage(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.hideSoftInput(view: View) {
    val im = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    im.hideSoftInputFromWindow(view.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
}