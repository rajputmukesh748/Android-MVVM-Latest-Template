package com.mukesh.apptunix.ui.socialLogin

import androidx.annotation.Keep

@Keep
data class SocialLoginData(
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var phoneNumber: String = "",
    var googleId: String = "",
    var facebookId: String = "",
    var image: String = "",
)
