package org.bmsk.presentation.model

data class GuideState(
    val text: Int,
    val partialText: Int? = null,
    val isSendButtonEnabled: Boolean = false,
    val loading: Boolean = false,
)
