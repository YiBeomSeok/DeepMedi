package org.bmsk.presentation.ui.viewholder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import org.bmsk.domain.usecase.HealthStatusDeterminer
import org.bmsk.presentation.BR
import org.bmsk.presentation.databinding.ItemHealthStateBinding
import org.bmsk.presentation.model.ListItem
import org.bmsk.presentation.model.UserHealthStateContentItem
import org.bmsk.presentation.model.UserHealthStateItem

abstract class BindingViewHolder<VB : ViewDataBinding>(
    private val binding: VB,
) : RecyclerView.ViewHolder(binding.root) {

    protected var item: ListItem? = null

    open fun bind(item: ListItem) {
        this.item = item
        binding.setVariable(BR.item, this.item)
    }
}