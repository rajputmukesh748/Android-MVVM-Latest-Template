package com.mukesh.template.utils

import android.text.InputFilter
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import java.util.regex.Matcher
import java.util.regex.Pattern


/**
 * Alphabets Filters
 * */
const val ALPHABET_SPACE_REGEX = "^[a-zA-Z ]+$"
const val ALPHABET_WITHOUT_SPACE_REGEX = "^[a-zA-Z]+$"
const val ALPHABET_NUMERIC_WITHOUT_SPACE_REGEX = "^[a-zA-Z0-9]+$"
const val ALPHABET_NUMERIC_SPACE_REGEX = "^[a-zA-Z0-9 ]+$"


/**
 * Alphabet Filters
 * */
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