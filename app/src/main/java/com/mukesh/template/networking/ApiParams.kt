package com.mukesh.template.networking

import com.mukesh.template.model.UserDataDC
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiParams {

    @GET(DEMO_API)
    suspend fun demoApi(
        @Query("page") pageNumber: Int = 1,
        @Query("loadSize") loadSize: Int = 10
    ) : List<UserDataDC>

}