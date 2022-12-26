package com.mukesh.template.utils

import kotlinx.coroutines.*


/**
 * Dispatchers
 * */
val mainDispatcher by lazy { Dispatchers.Main }
val ioDispatcher by lazy { Dispatchers.IO }
val defaultDispatcher by lazy { Dispatchers.Default }
val unconfinedDispatcher by lazy { Dispatchers.Unconfined }


/**
 * Create a main thread
 * */
fun mainThread(coroutineScope: suspend CoroutineScope.() -> Unit) =
    CoroutineScope(coroutineException + Job() + mainDispatcher).launch { coroutineScope() }


/**
 * Create a io thread
 * */
fun ioThread(coroutineScope: suspend CoroutineScope.() -> Unit) =
    CoroutineScope(coroutineException + Job() + ioDispatcher).launch { coroutineScope() }


/**
 *Create a default thread
 *  */
fun defaultThread(coroutineScope: suspend CoroutineScope.() -> Unit) =
    CoroutineScope(coroutineException + Job() + defaultDispatcher).launch { coroutineScope() }


/**
 * Create a unconfined thread
 * */
fun unconfinedThread(coroutineScope: suspend CoroutineScope.() -> Unit) =
    CoroutineScope(coroutineException + Job() + unconfinedDispatcher).launch { coroutineScope() }


/**
 * Coroutine Exception
 * */
val coroutineException = CoroutineExceptionHandler { _, throwable ->
    Logger.error("Error Throw", "Error throw in coroutine:- ${throwable.localizedMessage}")
}