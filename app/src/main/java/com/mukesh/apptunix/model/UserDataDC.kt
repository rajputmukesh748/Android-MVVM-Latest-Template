package com.mukesh.apptunix.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserDataDC(
    @SerializedName("name")
    var name: String? = null
)
