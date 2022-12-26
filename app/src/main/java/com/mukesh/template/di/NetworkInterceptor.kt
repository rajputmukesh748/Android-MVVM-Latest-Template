package com.mukesh.template.di

import com.mukesh.template.networking.HttpStatusCode
import com.mukesh.template.networking.hasNetwork
import okhttp3.Interceptor
import okhttp3.Response


/**
 * Status Code Interceptor
 * */
object NetworkInterceptor {


    /**
     * Status Code Handler
     * */
    val interceptor = Interceptor { chain ->
        var request = chain.request()

        /** Handle Data In Cache */
        request = request.newBuilder().apply {
            header(HttpStatusCode.ACCEPT, HttpStatusCode.APPLICATION_JSON)
            header(HttpStatusCode.CONTENT_TYPE, HttpStatusCode.APPLICATION_JSON)
            method(request.method, request.body)
            if (hasNetwork())
                header(HttpStatusCode.CACHE_CONTROL, "public, max-age=" + 5)
            else
                header(
                    HttpStatusCode.CACHE_CONTROL,
                    "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                )
        }.build()


        /**
         * Handle Api Response
         * */
        val response = chain.proceed(request)
        handleStatueCode(response)

        response
    }


    /**
     * Handle Status Code
     * */
    private fun handleStatueCode(response: Response) {
        //Check Status Code
        when (response.code) {
            //Handle Codes
            HttpStatusCode.UN_AUTHORIZE -> Unit
        }
    }

}