package com.mukesh.template.utils

import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


/**
 * Activity Back Press Call Back
 * */
fun AppCompatActivity.onBackPress(callback: () -> Unit) {
    onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            callback()
        }
    })
}


/**
 * Activity Back Press Call Back
 * */
fun Fragment.onBackPress(callback: () -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            callback()
        }
    })
}