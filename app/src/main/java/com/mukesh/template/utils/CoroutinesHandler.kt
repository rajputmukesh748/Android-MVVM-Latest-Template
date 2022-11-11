package com.mukesh.template.utils

import kotlinx.coroutines.*

/**
 * Create a main thread
 * */
fun mainThread(coroutineScope: suspend CoroutineScope.() -> Unit) =
    CoroutineScope(Job() + Dispatchers.Main + coroutineExceptionHandler).launch { coroutineScope() }


/**
 * Create a io thread
 * */
fun ioThread(coroutineScope: suspend CoroutineScope.() -> Unit) =
    CoroutineScope(Job() + Dispatchers.IO + coroutineExceptionHandler).launch { coroutineScope() }


/**
 *Create a default thread
 *  */
fun defaultThread(coroutineScope: suspend CoroutineScope.() -> Unit) =
    CoroutineScope(Job() + Dispatchers.Default + coroutineExceptionHandler).launch { coroutineScope() }


/**
 * Create a unconfined thread
 * */
fun unconfinedThread(coroutineScope: suspend CoroutineScope.() -> Unit) =
    CoroutineScope(Job() + Dispatchers.Unconfined + coroutineExceptionHandler).launch { coroutineScope() }


/**
 * Coroutine Exception Handler
 * */
val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    throwable.printStackTrace()
}