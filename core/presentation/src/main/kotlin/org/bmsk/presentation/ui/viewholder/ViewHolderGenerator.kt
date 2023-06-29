package org.bmsk.presentation.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import org.bmsk.presentation.databinding.ItemEmptyBinding
import org.bmsk.presentation.model.ViewType

object ViewHolderGenerator {

    fun get(
        parent: ViewGroup,
        viewType: Int,
    ): BindingViewHolder<*> {
        return when (viewType) {
            ViewType.USER_PROFILE.ordinal -> UserProfileViewHolder(parent.toBinding())
            ViewType.USER_HEALTH_STATE.ordinal -> UserHealthStateViewHolder(parent.toBinding())
            else -> ItemViewHolder(parent.toBinding())
        }
    }

    class ItemViewHolder(binding: ItemEmptyBinding) : BindingViewHolder<ItemEmptyBinding>(binding)

    private inline fun <reified V : ViewBinding> ViewGroup.toBinding(): V {
        return V::class.java.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        ).invoke(null, LayoutInflater.from(context), this, false) as V
    }
}