package org.bmsk.network.model

import com.google.gson.annotations.SerializedName

data class UploadImageRes(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String
)
