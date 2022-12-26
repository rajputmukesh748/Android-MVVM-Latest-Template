package com.mukesh.template.commonClasses.singleClickListener

import android.view.View

/**
 * On Single Click Listener
 * */
object SingleClick {

    /**
     * On Single Click Listener
     * */
    interface OnSingleClickListener {
        fun onClick(view: View?)
    }
}

/**
 * Set On Single Click Listener
 * */
fun View.setOnSingleClickListener(onSingleClickListener: SingleClick.OnSingleClickListener) {
    SingleClickListenerHandler(view = this, onSingleClickListener = onSingleClickListener)
}


/**
 * Set On Single Click Listener
 * */
fun View.setOnSingleClickListener(callBack: View.() -> Unit){
    SingleClickListenerHandler(view = this, onSingleClickListener = object : SingleClick.OnSingleClickListener {
        override fun onClick(view: View?) {
            callBack()
        }
    })
}
