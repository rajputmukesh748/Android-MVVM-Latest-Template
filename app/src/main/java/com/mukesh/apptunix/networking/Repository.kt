package com.mukesh.apptunix.networking

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Repository For API Data Emit into Flow
 * */
class Repository @Inject constructor(
    private val apiParams: ApiParams
) {

    /**
     * Dummy Data Api Call
     * */
    suspend fun dummyData() = flow {
        emit(apiParams.demoApi())
    }.flowOn(Dispatchers.IO)

}