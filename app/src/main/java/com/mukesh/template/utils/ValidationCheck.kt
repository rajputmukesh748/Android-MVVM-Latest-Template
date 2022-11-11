package com.mukesh.template.utils

import android.text.TextUtils
import android.util.Patterns


/**
 * If Any Key Required
 * */
fun Any.isRequired(): Boolean =
    TextUtils.isEmpty(this.toString().trim())


/**
 * If String is a email
 * */
fun String.isValidEmail(): Boolean = !Patterns.EMAIL_ADDRESS.matcher(this.trim()).matches()


/**
 * Validate Password
 * */
fun String.isValidPassword() = this.trim().length < 7


/**
 * Validate Name
 * */
fun String.isValidName() = this.trim().length < 2


/**
 * Password Match
 * */
fun String.isPasswordMatch(password: String) =
    !this.trim().contentEquals(password.trim(), ignoreCase = false)


/**
 * Validate Phone Number
 * */
fun String.isValidNumber() =
    this.trim().length < 7 || this.trim().length > 15 || !Patterns.PHONE.matcher(this.trim())
        .matches()


/**
 * Check Url Valid Or Not
 * */
fun String.validUrl() = !Patterns.WEB_URL.matcher(this.trim()).matches()