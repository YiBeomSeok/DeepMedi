package org.bmsk.domain.usecase

import org.bmsk.domain.model.HealthStatus

object HealthStatusDeterminer {

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
