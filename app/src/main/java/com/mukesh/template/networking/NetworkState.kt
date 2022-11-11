package com.mukesh.template.networking

/**
 * Network State Handler
 * */
sealed class NetworkState<T>(val data: T? = null, val error: Any? = null) {

    class LOADING<T> : NetworkState<T>()

    class SUCCESS<T>(data: T?) : NetworkState<T>(data = data)

    class ERROR<T>(error: Any?) : NetworkState<T>(error = error)

}