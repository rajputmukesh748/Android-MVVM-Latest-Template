package com.mukesh.template.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserDataDC(
    @SerializedName("userId")
    var userId: String? = null,
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("completed")
    var completed: String? = null
)
