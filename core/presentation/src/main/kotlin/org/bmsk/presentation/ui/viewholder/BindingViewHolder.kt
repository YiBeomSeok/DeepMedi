package org.bmsk.presentation.ui.viewholder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import org.bmsk.presentation.BR
import org.bmsk.presentation.databinding.ItemHealthStateBinding
import org.bmsk.presentation.model.HealthStatus
import org.bmsk.presentation.model.ListItem
import org.bmsk.presentation.model.UserHealthStateContentItem
import org.bmsk.presentation.model.UserHealthStateItem

abstract class BindingViewHolder<VB : ViewDataBinding>(
    private val binding: VB
) : RecyclerView.ViewHolder(binding.root) {

    protected var item: ListItem? = null
    private val healthStatusDeterminer = HealthStatusDeterminer()

    open fun bind(item: ListItem) {
        this.item = item
        binding.setVariable(BR.item, this.item)

        if (binding is ItemHealthStateBinding) {
            bindHealthStateItem(item as UserHealthStateItem)
        }
    }

    private fun bindHealthStateItem(item: UserHealthStateItem) {
        with(binding as ItemHealthStateBinding) {
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
                    detailDescription = if(item.alcohol) "검출" else "미검출"
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

class HealthStatusDeterminer {

    fun getBpmStatus(bpm: Int) = when (bpm) {
        in 60..80 -> HealthStatus.NORMAL
        in 81..100 -> HealthStatus.CAUTION
        in 101..150 -> HealthStatus.WARNING
        in 151..200 -> HealthStatus.DANGER
        else -> HealthStatus.NONE
    }

    fun getSysStatus(sys: Int) = when (sys) {
        in 100..120 -> HealthStatus.NORMAL
        in 121..140 -> HealthStatus.CAUTION
        in 141..160 -> HealthStatus.WARNING
        in 161..200 -> HealthStatus.DANGER
        else -> HealthStatus.NONE
    }

    fun getDiaStatus(dia: Int) = when (dia) {
        in 50..70 -> HealthStatus.NORMAL
        in 70..90 -> HealthStatus.CAUTION
        in 71..110 -> HealthStatus.WARNING
        in 91..120 -> HealthStatus.DANGER
        else -> HealthStatus.NONE
    }

    fun getRespStatus(resp: Int) = when (resp) {
        in 1..8 -> HealthStatus.NORMAL
        in 9..12 -> HealthStatus.CAUTION
        in 12..16 -> HealthStatus.WARNING
        in 13..20 -> HealthStatus.DANGER
        else -> HealthStatus.NONE
    }

    fun getFatigueStatus(fatigue: Int) = when (fatigue) {
        in 60..80 -> HealthStatus.NORMAL
        in 81..100 -> HealthStatus.CAUTION
        in 101..150 -> HealthStatus.WARNING
        in 151..200 -> HealthStatus.DANGER
        else -> HealthStatus.NONE
    }

    fun getStressStatus(stress: Int) = when (stress) {
        in 0..1 -> HealthStatus.NORMAL
        2 -> HealthStatus.CAUTION
        in 3..4 -> HealthStatus.WARNING
        5 -> HealthStatus.DANGER
        else -> HealthStatus.NONE
    }

    fun getCardiovascularStatus(sys: Int, dia: Int): HealthStatus {
        val sysStatus = getSysStatus(sys)
        val diaStatus = getDiaStatus(dia)

        // 우선 순위를 둔다: NONE < NORMAL < CAUTION < WARNING < DANGER
        return when {
            sysStatus == HealthStatus.DANGER || diaStatus == HealthStatus.DANGER -> HealthStatus.DANGER
            sysStatus == HealthStatus.WARNING || diaStatus == HealthStatus.WARNING -> HealthStatus.WARNING
            sysStatus == HealthStatus.CAUTION || diaStatus == HealthStatus.CAUTION -> HealthStatus.CAUTION
            sysStatus == HealthStatus.NORMAL && diaStatus == HealthStatus.NORMAL -> HealthStatus.NORMAL
            else -> HealthStatus.NONE
        }
    }
}
