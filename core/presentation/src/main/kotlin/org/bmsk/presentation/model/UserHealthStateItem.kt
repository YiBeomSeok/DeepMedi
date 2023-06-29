package org.bmsk.presentation.model

data class UserHealthStateItem(
    val bpm: Int,
    val sys: Int,
    val dia: Int,
    val resp: Int,
    val fatigue: Int,
    val stress: Int,
    val temp: Float,
    val alcohol: Boolean,
    val spo2: Int,
) : ListItem {
    override val viewType: ViewType
        get() = ViewType.USER_HEALTH_STATE
}

enum class HealthStatus {
    NORMAL,
    CAUTION,
    WARNING,
    DANGER,
    NONE,
}

data class UserHealthStateContentItem(
    val title: String,
    val detailDescription: String,
    val status: HealthStatus = HealthStatus.NONE
)