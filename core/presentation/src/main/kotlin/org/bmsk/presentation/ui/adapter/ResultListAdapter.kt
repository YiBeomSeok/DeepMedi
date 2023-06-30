/**
 * ListAdapter는 확장 가능성을 고려하였습니다.
 * ViewType에 따라 ViewHolder를 만들 수 있도록 하였습니다.
 */

package org.bmsk.presentation.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.bmsk.presentation.model.ListItem
import org.bmsk.presentation.ui.viewholder.BindingViewHolder
import org.bmsk.presentation.ui.viewholder.ViewHolderGenerator

class ResultListAdapter
    : ListAdapter<ListItem, BindingViewHolder<*>>(DiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return item?.viewType?.ordinal ?: -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<*> {
        return ViewHolderGenerator.get(parent, viewType)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<*>, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }
}