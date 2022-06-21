package com.mukesh.apptunix.utils

import android.text.TextUtils
import java.util.regex.Pattern


/**
 * If Any Key Required
 * */
fun Any.isRequired() : Boolean =
    TextUtils.isEmpty(this.toString().trim())


/**
 * If String is a email
 * */
fun String.isValidEmail() : Boolean = !Pattern.compile(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z]{2,5}" +
            ")+"
).matcher(this.trim()).matches()


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
fun String.isPasswordMatch(password : String) =
    !this.trim().contentEquals(password.trim(),ignoreCase = false)


/**
 * Validate Phone Number
 * */
fun String.isValidNumber() = this.trim().length < 7 || this.trim().length > 15
