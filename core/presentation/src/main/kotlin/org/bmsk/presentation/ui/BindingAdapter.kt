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
import org.bmsk.domain.model.HealthStatus
import org.bmsk.presentation.R

@BindingAdapter("partialColor")
fun setPartialColor(textView: TextView, partialText: String) {
    val fullText = textView.text.toString()
    val spannableString = SpannableStringBuilder(fullText)

    val startIndex = fullText.indexOf(partialText)
    if (startIndex != -1) {
        val endIndex = startIndex + partialText.length

        val foregroundColorSpan = ForegroundColorSpan(Color.RED)
        spannableString.setSpan(
            foregroundColorSpan,
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
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

@BindingAdapter("setHealthStatus")
fun ImageView.setHealthStatus(healthStatus: HealthStatus) {
    when (healthStatus) {
        HealthStatus.NORMAL -> setImageResource(R.drawable.ic_normal)
        HealthStatus.CAUTION -> setImageResource(R.drawable.ic_caution)
        HealthStatus.WARNING -> setImageResource(R.drawable.ic_warning)
        HealthStatus.DANGER -> setImageResource(R.drawable.ic_danger)
        else -> {}
    }
}