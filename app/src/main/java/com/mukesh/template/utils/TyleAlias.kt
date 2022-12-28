package com.mukesh.template.utils

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.mukesh.template.R

typealias COLOR_ALIAS = R.color

typealias ID_ALIAS = R.id

typealias STRING_ALIAS = R.string

typealias DRAWABLE_ALIAS = R.drawable

typealias STYLE_ALIAS = R.style


/**
 * Get Color Value
 * */
fun Context.getColorValue(@ColorRes color: Int) = ContextCompat.getColor(this, color)


/**
 * Get Drawable Value
 * */
fun Context.getDrawableValue(@DrawableRes drawable: Int) = ContextCompat.getDrawable(this, drawable)


/**
 * Get String Value
 * */
fun Context.getStringValue(@StringRes string: Int) = getString(string)