package org.bmsk.domain.model

data class AnalyzedInfo(
    val name: String,
    val profile: String,
    val cumulantMinusPoint: Int,
    val bpm: Int,
    val sys: Int,
    val dia: Int,
    val resp: Int,
    val fatigue: Int,
    val stress: Int,
    val temp: Float,
    val alcohol: Boolean,
    val spo2: Int,
) {
    companion object {
        fun emptyInfo() = AnalyzedInfo(
            name = "",
            profile = "",
            cumulantMinusPoint = 0,
            bpm = 0,
            sys = 0,
            dia = 0,
            resp = 0,
            fatigue = 0,
            stress = 0,
            temp = 0F,
            alcohol = false,
            spo2 = 0,
        )
    }
}
