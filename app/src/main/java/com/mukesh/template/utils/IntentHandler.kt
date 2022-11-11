package com.mukesh.template.utils

import android.app.Activity
import android.content.Intent

/**
 * Change Intent Data
 * */
inline fun <reified T> Activity.changeIntent(finish: Boolean = false, finishAffinity: Boolean = false){
    Intent(this,T::class.java).apply {
        startActivity(this)
        if (finish) finish()
        if (finishAffinity) finishAffinity()
    }
}
