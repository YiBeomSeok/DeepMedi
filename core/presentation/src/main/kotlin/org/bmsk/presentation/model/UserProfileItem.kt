package org.bmsk.presentation.model

data class UserProfileItem(
    val name: String,
    val profile: String,
    val cumulantMinusPoint: Int,
) : ListItem {
    override val viewType: ViewType
        get() = ViewType.USER_PROFILE
}
