package com.mukesh.template.utils

import android.text.InputFilter
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.util.Patterns
import java.util.regex.Matcher
import java.util.regex.Pattern


/**
 * If Any Key Required
 * */
fun String?.isRequired(): Boolean = TextUtils.isEmpty(this?.trim().orEmpty())


/**
 * If String is a email
 * */
fun String?.isValidEmail(): Boolean =
    !Patterns.EMAIL_ADDRESS.matcher(this?.trim().orEmpty()).matches()


/**
 * Validate Password
 * */
fun String?.isValidPassword(): Boolean {
    val passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@*#$%^&+=!])(?=\\S+$).{8,50}$"
    val pattern = Pattern.compile(passwordPattern)
    return !pattern.matcher(this.orEmpty()).matches()
}


/**
 * Validate Name
 * */
fun String?.isValidName() = (this?.trim()?.length ?: 0) < 2


/**
 * Validate Name
 * */
infix fun String?.isValidLength(length: Int) = (this?.trim()?.length ?: 0) < length


/**
 * Password Match
 * */
infix fun String?.isMatch(password: String?) =
    !this?.trim().orEmpty().contentEquals(password?.trim().orEmpty(), ignoreCase = false)


/**
 * Validate Phone Number
 * */
fun String?.isValidNumber() = !TextUtils.isDigitsOnly(this?.trim().orEmpty())


/**
 * Check Url Valid Or Not
 * */
fun String?.validUrl() = !Patterns.WEB_URL.matcher(this?.trim().orEmpty()).matches()


/**
 * Alphabets Filters
 * */
const val ALPHABET_SPACE_REGEX = "^[a-zA-Z ]+$"
const val ALPHABET_WITHOUT_SPACE_REGEX = "^[a-zA-Z]+$"

fun alphabetFilter(regex: String = ALPHABET_SPACE_REGEX): InputFilter {
    return object : InputFilter {
        override fun filter(
            source: CharSequence,
            start: Int,
            end: Int,
            dest: Spanned,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            var keepOriginal = true
            val sb = StringBuilder(end - start)
            for (i in start until end) {
                val c = source[i]
                if (isCharAllowed(c)) // put your condition here
                    sb.append(c) else keepOriginal = false
            }
            return if (keepOriginal) null else {
                if (source is Spanned) {
                    val sp = SpannableString(sb)
                    TextUtils.copySpansFrom(source, start, sb.length, null, sp, 0)
                    sp
                } else {
                    sb
                }
            }
        }

        private fun isCharAllowed(c: Char): Boolean {
            val ps = Pattern.compile(regex)
            val ms: Matcher = ps.matcher(c.toString())
            return ms.matches()
        }
    }
}