package org.bmsk.presentation.ui.viewholder

import org.bmsk.domain.usecase.HealthStatusDeterminer
import org.bmsk.presentation.BR
import org.bmsk.presentation.databinding.ItemHealthStateBinding
import org.bmsk.presentation.databinding.ItemHealthStateContentBinding
import org.bmsk.presentation.model.ListItem
import org.bmsk.presentation.model.UserHealthStateContentItem
import org.bmsk.presentation.model.UserHealthStateItem

class UserHealthStateViewHolder(
    private val binding: ItemHealthStateBinding,
): BindingViewHolder<ItemHealthStateBinding>(binding) {

    private val healthStatusDeterminer = HealthStatusDeterminer

    override fun bind(item: ListItem) {
        super.bind(item)

        item as UserHealthStateItem
        bindHealthStateItem(item)
    }

    private fun bindHealthStateItem(item: UserHealthStateItem) {
        with(binding) {
            heartRatePerMinuteView.setVariable(
                BR.item, UserHealthStateContentItem(
                    title = "분당 심박수",
                    detailDescription = "${item.bpm}회/분",
                    status = healthStatusDeterminer.getBpmStatus(item.bpm)
                )
            )
            cardiovascularHealthView.setVariable(
                BR.item, UserHealthStateContentItem(
                    title = "심혈관 건강",
                    detailDescription = "${item.sys}/${item.dia}",
                    status = healthStatusDeterminer.getCardiovascularStatus(item.sys, item.dia)
                )
            )
            respiratoryRatePerMinuteView.setVariable(
                BR.item, UserHealthStateContentItem(
                    title = "분당 호흡수",
                    detailDescription = "${item.resp}/분",
                    status = healthStatusDeterminer.getRespStatus(item.resp)
                )
            )
            fatigueView.setVariable(
                BR.item, UserHealthStateContentItem(
                    title = "피로도",
                    detailDescription = "${item.fatigue}",
                    status = healthStatusDeterminer.getFatigueStatus(item.fatigue)
                )
            )
            stressView.setVariable(
                BR.item, UserHealthStateContentItem(
                    title = "스트레스",
                    detailDescription = "${item.stress}",
                    status = healthStatusDeterminer.getStressStatus(item.stress)
                )
            )
            temperatureView.setVariable(
                BR.item, UserHealthStateContentItem(
                    title = "체온",
                    detailDescription = String.format("%.1f°C", item.temp)
                )
            )
            alcholLevelView.setVariable(
                BR.item, UserHealthStateContentItem(
                    title = "알코올 농도",
                    detailDescription = if (item.alcohol) "검출" else "미검출"
                )
            )
            spo2View.setVariable(
                BR.item, UserHealthStateContentItem(
                    title = "SpO2",
                    detailDescription = "${item.spo2}%",
                )
            )
        }
    }
}

class UserHealthStateContentViewHolder(
    binding: ItemHealthStateContentBinding
): BindingViewHolder<ItemHealthStateContentBinding>(binding)