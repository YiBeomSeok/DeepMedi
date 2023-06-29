package org.bmsk.network.model

import com.google.gson.annotations.SerializedName

data class MeasuredResultRes(
    @SerializedName("name")
    val name: String,
    @SerializedName("profile")
    val profile: String,
    @SerializedName("cumulant_minus_point")
    val cumulantMinusPoint: Int,
    @SerializedName("bpm")
    val bpm: Int,
    @SerializedName("sys")
    val sys: Int,
    @SerializedName("dia")
    val dia: Int,
    @SerializedName("resp")
    val resp: Int,
    @SerializedName("fatigue")
    val fatigue: Int,
    @SerializedName("stress")
    val stress: Int,
    @SerializedName("temp")
    val temp: Float,
    @SerializedName("alcohol")
    val alcohol: Boolean,
    @SerializedName("spo2")
    val spo2: Int,
)