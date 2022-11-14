package com.mukesh.template.networking.repository

import androidx.paging.PagingSource
import com.mukesh.template.commonClasses.genericAdapter.recyclerAdapter.GenericPagingSource
import com.mukesh.template.model.UserDataDC
import com.mukesh.template.networking.ApiParams
import javax.inject.Inject


/**
 * Paging Handler
 * */
class PagingHandlerRepository @Inject constructor(
    private val apiParams: ApiParams
) : GenericPagingSource<UserDataDC>() {

    private var extraParam: HashMap<String, Any>? = null


    /**
     * Call Api For Load Data and also handle network request
     * */
    override suspend fun callApiData(pageNumber: Int, loadSize: Int): List<UserDataDC> {
        apiParams.demoApi(pageNumber, loadSize).let {
            return it
        }
    }


    /**
     * Paging Source Factory Call Back
     * */
    override fun getPagingSourceFactory(): PagingSource<Int, UserDataDC> {
        return PagingHandlerRepository(apiParams)
    }


    /**
     * Set Extra Params If Any
     * */
    fun setExtraParam(extraParam: HashMap<String, Any>){
        this.extraParam = extraParam
    }
}