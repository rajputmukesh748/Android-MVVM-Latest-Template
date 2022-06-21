package com.mukesh.apptunix.di

import okhttp3.Interceptor

/**
 * Status Code Interceptor
 * */
object StatusCodeInterceptor {

    /**
     * Status Code Handler
     * */
    val statusInterceptor = Interceptor { chain ->
        val request = chain.request()

        val response = chain.proceed(request)

        //Check Status Code
        when(response.code){
            //Handle Codes

        }

        response
    }

}