package org.bmsk.presentation.ui

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("partialColor")
fun setPartialColor(textView: TextView, partialText: String) {
    val fullText = textView.text.toString()
    val spannableString = SpannableStringBuilder(fullText)

    val startIndex = fullText.indexOf(partialText)
    if (startIndex != -1) {
        val endIndex = startIndex + partialText.length

        val foregroundColorSpan = ForegroundColorSpan(Color.RED)
        spannableString.setSpan(foregroundColorSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    textView.text = spannableString
}

@BindingAdapter("visibleOrGone")
fun setVisibility(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("setImage")
fun ImageView.setImage(imageUrl: String) {
    Glide.with(this)
        .load(imageUrl)
        .circleCrop()
        .into(this)
}