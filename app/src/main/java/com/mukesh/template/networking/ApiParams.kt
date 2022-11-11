package com.mukesh.template.networking

import com.mukesh.template.model.UserDataDC
import retrofit2.Response
import retrofit2.http.GET

interface ApiParams {

    @GET(DEMO_API)
    suspend fun demoApi() : Response<BaseResponse<UserDataDC>>

}