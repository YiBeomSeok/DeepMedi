package org.bmsk.presentation.ui.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import org.bmsk.presentation.model.ListItem

class DiffCallback<T: ListItem>: DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.viewType == newItem.viewType && oldItem.getKey() == newItem.getKey()
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}