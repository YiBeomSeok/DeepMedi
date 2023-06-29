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
)
