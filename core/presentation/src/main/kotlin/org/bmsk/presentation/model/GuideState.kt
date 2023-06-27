package org.bmsk.presentation.model

data class GuideState(
    val text: String,
    val partialText: String? = null,
    val isSendButtonEnabled: Boolean = false,
)
