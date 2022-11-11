package com.mukesh.template.controller

import android.content.Context
import android.graphics.Typeface


/**
 * Font Override
 * */
object FontsOverride {

    /**
     * Set Default Font
     * */
    fun setDefaultFont(
        context: Context,
        staticTypefaceFieldName: String, fontAssetName: String
    ) {
        val regular = Typeface.createFromAsset(context.assets, fontAssetName)
        replaceFont(staticTypefaceFieldName, regular)
    }


    /**
     * Replace Font
     * */
    private fun replaceFont(
        staticTypefaceFieldName: String,
        newTypeface: Typeface
    ) {
        try {
            val staticField = Typeface::class.java.getDeclaredField(staticTypefaceFieldName)
            staticField.isAccessible = true
            staticField.set(null, newTypeface)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

    }
}