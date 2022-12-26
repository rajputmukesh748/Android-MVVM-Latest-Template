package com.mukesh.template.commonClasses.singleClickListener

import android.os.SystemClock
import android.view.View
import com.mukesh.template.commonClasses.singleClickListener.SingleClick
import java.util.concurrent.TimeUnit

/**
 * Single Click Listener Handler
 * */
class SingleClickListenerHandler(
    view: View,
    private val onSingleClickListener: SingleClick.OnSingleClickListener
) : View.OnClickListener {

    /**
     * Store Calculate Time
     * */
    private var lastClickTime = 0L


    /**
     * Initializer
     * */
    init {
        view.setOnClickListener(this)
    }


    /**
     * Perform Click
     * */
    override fun onClick(view: View?) {
        if ((SystemClock.elapsedRealtime() - lastClickTime) < TimeUnit.SECONDS.toMillis(1)) {
            return
        }
        lastClickTime = SystemClock.elapsedRealtime()
        onSingleClickListener.onClick(view = view)
    }
}