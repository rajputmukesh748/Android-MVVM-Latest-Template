package com.mukesh.template.utils

import android.util.Log
import com.mukesh.template.BuildConfig

/**
 * @Logger
 * Print log messages only in debug file
 * */
object Logger {

    /**
     * Debug Log Handler
     * */
    fun debug(tag: String = "", message: String = "") {
        if (BuildConfig.DEBUG)
            Log.d(tag, message)
    }

    /**
     * Error Log Handler
     * */
    fun error(tag: String = "", message: String = "") {
        if (BuildConfig.DEBUG)
            Log.e(tag, message)
    }

}