package com.mukesh.template.networking

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * Base Response If You Need
 * */
@Keep
data class BaseResponse<T>(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("data")
    var data: T? = null
)