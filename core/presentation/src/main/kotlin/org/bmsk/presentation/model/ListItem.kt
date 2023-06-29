package org.bmsk.presentation.model

import java.io.Serializable

interface ListItem: Serializable {
    val viewType: ViewType

    fun getKey() = hashCode()
}

enum class ViewType {
    USER_PROFILE,
    USER_HEALTH_STATE,

    EMPTY,
}
