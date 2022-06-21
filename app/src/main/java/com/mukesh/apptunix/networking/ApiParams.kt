package com.mukesh.apptunix.networking

import com.mukesh.apptunix.model.UserDataDC
import retrofit2.Response
import retrofit2.http.GET

interface ApiParams {

    @GET(DEMO_API)
    suspend fun demoApi() : Response<BaseResponse<UserDataDC>>

}