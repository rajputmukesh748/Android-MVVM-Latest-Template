package com.mukesh.apptunix.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Create a main thread
 * */
fun mainThread(coroutineScope: suspend CoroutineScope.() -> Unit) =
    CoroutineScope(Dispatchers.Main).launch { coroutineScope() }


/**
 * Create a io thread
 * */
fun ioThread(coroutineScope: suspend CoroutineScope.() -> Unit) =
    CoroutineScope(Dispatchers.IO).launch { coroutineScope() }


/**
 *Create a default thread
 *  */
fun defaultThread(coroutineScope: suspend CoroutineScope.() -> Unit) =
    CoroutineScope(Dispatchers.Default).launch { coroutineScope() }


/**
 * Create a unconfined thread
 * */
fun unconfinedThread(coroutineScope: suspend CoroutineScope.() -> Unit) =
    CoroutineScope(Dispatchers.Unconfined).launch { coroutineScope() }
